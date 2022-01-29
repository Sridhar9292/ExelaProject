package com.exelatech.ecxapi.controller;

import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.BillerDetailMapper;
import com.exelatech.ecxapi.model.BillerDetail;
import com.exelatech.ecxapi.model.Comment;
import com.exelatech.ecxapi.model.Dashboard;
import com.exelatech.ecxapi.model.DataFeed;
import com.exelatech.ecxapi.repository.DashboardRepository;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/remit-dashboard")
public class RemitDashboardController {

	@Autowired
	private DashboardRepository dashboardRepository;

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${spring.activemq.user}")
	private String activeMqUser;

	@Value("${spring.activemq.password}")
	private String activeMqPassword;

	@Value("${spring.activemq.broker-url}")
	private String activeMqUrl;

	@Value("${spring.activemq.event-topic}")
	private String eventTopic;
	
	@Autowired
	private BillerDetailMapper billerDetailMapper;

	@PreAuthorize("hasAnyAuthority('_remit:remitDashboard:view','_remit:remitDashboard:manage')")
	@GetMapping("/{id}")
	public Dashboard getDashboardById(@PathVariable String id) throws NotFoundException {
		Dashboard db = null;
		try {
			GetRequest get = new GetRequest("console", id);
			GetResponse resp = client.get(get, RequestOptions.DEFAULT);

			if (resp.isExists()) {
				db = objectMapper.readValue(resp.getSourceAsString(), Dashboard.class);
				for (DataFeed remitFeed : db.getRemitFeeds()) {
					int comments = remitFeed.getComments().size();
					if (comments > 0) {
						remitFeed.setCommentsPresent(true);
					} else {
						remitFeed.setCommentsPresent(false);
					}
				}
				System.out.println("Db is: " + db);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return db;
	}

	@PreAuthorize("hasAnyAuthority('_remit:remitDashboard:comment','_remit:remitDashboard:manage')")
	@PostMapping("/comments/{id}/{feedIndex}")
	public ResponseEntity<String> addComments(@PathVariable String id, @PathVariable int feedIndex,
			@RequestBody Comment comment) throws NotFoundException {
		try {
			Dashboard dashboard = dashboardRepository.findById(id).get();
			String commentedBy = SecurityContextHolder.getContext().getAuthentication().getName();
			comment.setTimestamp(new Date());
			comment.setCommentedBy(commentedBy);
			DataFeed df = dashboard.getRemitFeeds().get(feedIndex);
			df.getComments().add(comment);
			dashboardRepository.save(dashboard);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}

	@PreAuthorize("hasAnyAuthority('_remit:remitDashboard:manage')")
	@PostMapping("/remitexport/{feedDocID}/{clientCode}/{clientLobCode}/{delivery}/{billerCode}/{vendor}/")
	public ResponseEntity<String> generateRemitExport(@PathVariable("feedDocID") String feedDocID,
			@PathVariable("clientCode") String clientCode, @PathVariable("clientLobCode") String clientLobCode,
			@PathVariable("delivery") String delivery, @PathVariable("billerCode") String billerCode,
			@PathVariable("vendor") String vendor) throws NotFoundException {

		try {
			
        	if(delivery.equals("TMSPL")){
        		BillerDetail billerDetail = billerDetailMapper.getBillerDeliveryDetails(clientCode, clientLobCode);
        		List<BillerDetail>processedBiller = billerDetailMapper.getProcessedBillerDetails(feedDocID, billerDetail.getBillerCode());
        		for(BillerDetail list :processedBiller){
        			reExport(feedDocID, list.getClientCode(), list.getClientLobCode(), list.getDelivery(), list.getBillerCode(), vendor);
        		}
        	}else {

			ConnectionFactory factory = new ActiveMQConnectionFactory(activeMqUser, activeMqPassword, activeMqUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(eventTopic);
			// Added as a producer
			MessageProducer producer = session.createProducer(topic);
			TextMessage msg = session.createTextMessage();
			
			msg.setStringProperty("eventName","BillerRemitDataExportRequest");
            msg.setStringProperty("feedDocID", feedDocID);
            msg.setStringProperty("clientCode", clientCode);
            msg.setStringProperty("clientLobCode", clientLobCode);
            msg.setStringProperty("delivery", delivery);
            msg.setStringProperty("reExportStatus", "ReExported");
            msg.setText("ECX Console - Biller RemitData Exported Event");
			producer.send(msg);
        	}

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
	}
	
	private void reExport(String feedDocID,String clientCode,String clientLobCode,String delivery,String billerCode,String vendor){
    	
    	try{
    		
    		ConnectionFactory factory = new ActiveMQConnectionFactory(activeMqUser, activeMqPassword, activeMqUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(eventTopic);
			MessageProducer producer = session.createProducer(topic);
			TextMessage msg = session.createTextMessage();
			
        msg.setStringProperty("eventName","BillerRemitDataExportRequest");
        msg.setStringProperty("feedDocID", feedDocID);
        msg.setStringProperty("clientCode", clientCode);
        msg.setStringProperty("clientLobCode", clientLobCode);
        msg.setStringProperty("lockboxNumber", billerCode);
        msg.setStringProperty("vendor", vendor);
        msg.setStringProperty("delivery", delivery);
        msg.setStringProperty("reExportStatus", "ReExported");
        msg.setText("ECX Console - Biller RemitData Exported Event");
        producer.send(msg);
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
}
