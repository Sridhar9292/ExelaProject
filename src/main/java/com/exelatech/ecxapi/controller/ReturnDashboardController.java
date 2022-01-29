package com.exelatech.ecxapi.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.exelatech.ecxapi.model.Comment;
import com.exelatech.ecxapi.model.ReturnDashboard;
import com.exelatech.ecxapi.model.ReturnDashboardView;
import com.exelatech.ecxapi.model.ReturnDataFeed;
import com.exelatech.ecxapi.repository.ReturnDashboardRepository;
import com.exelatech.ecxapi.util.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/returns-dashboard")
public class ReturnDashboardController {

	@Value("${prefix}")
	private String prefix;

	@Value("${context}")
	private String context;

	@Value("${vendors}")
	private String vendors;

	
	@Autowired
	private ReturnDashboardRepository returnDashboardRepository;

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;

	@PreAuthorize("hasAnyAuthority('_remit:returnDashboard:view','_remit:returnDashboard:manage')")
	@GetMapping("/{id}")
	public ReturnDashboardView getDashboardById(@PathVariable String id) throws NotFoundException {
		System.out.println("Came inside ReturnDashboardView");
		ReturnDashboard db = null;
		ReturnDashboardView view = null;
		try {
			GetRequest get = new GetRequest("return-console", id);
			GetResponse resp = client.get(get, RequestOptions.DEFAULT);
			if (resp.isExists()) {
				System.out.println("Came inside ReturnDashboardView present");
				db = objectMapper.readValue(resp.getSourceAsString(), ReturnDashboard.class);

				view = new ReturnDashboardView();
				view.setId(id);
				view.setDate(db.getDate());

				for (ReturnDataFeed returnDataFeed : db.getReturnFeeds()) {

					int comments = returnDataFeed.getComments().size();
					if (comments > 0) {
						returnDataFeed.setCommentsPresent(true);
					} else {
						returnDataFeed.setCommentsPresent(false);
					}

					if (returnDataFeed.getFeedDocID().contains("conf")) {
						view.getConfirmation().add(returnDataFeed);
					} else if (isVendors(returnDataFeed)) {
						view.getVendors().add(returnDataFeed);
					} else {				
						view.getDownstreams().add(returnDataFeed);
					}

				}

				System.out.println("Db is: " + db);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return view;
	}

	@PreAuthorize("hasAnyAuthority('_remit:returnDashboard:comment','_remit:returnDashboard:manage')")
	@PostMapping("/comments/{id}/{feedIndex}")
	public ResponseEntity<String> addComments(@PathVariable String id, @PathVariable int feedIndex,
			@RequestBody Comment comment) throws NotFoundException {
		try {
			ReturnDashboard returnDashboard = returnDashboardRepository.findById(id).get();
			String commentedBy = SecurityContextHolder.getContext().getAuthentication().getName();
			comment.setTimestamp(new Date());
			comment.setCommentedBy(commentedBy);
			ReturnDataFeed rdf = returnDashboard.getReturnFeeds().get(feedIndex);
			rdf.getComments().add(comment);
			returnDashboardRepository.save(returnDashboard);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return ResponseEntity.ok().body("Success");

	}
/*
	private boolean isDownstream(ReturnDataFeed returnDataFeed) {
		String feedDocID = returnDataFeed.getFeedDocID();
		String from = returnDataFeed.getFrom() != null ? returnDataFeed.getFrom().toLowerCase() : "";
		String regex = prefix + "." + from + ".*";
		return Pattern.compile(regex).matcher(feedDocID).matches();
	}
*/
	
   private boolean isVendors(ReturnDataFeed returnDataFeed) {
		boolean check=false;
		ArrayList<String> vendorsList =(ArrayList<String>) Stream.of(vendors.split(","))
                .collect(Collectors.toList());
		for (String str: vendorsList)
		{
			if(returnDataFeed.getFeedDocID().contains(str)) {
				return true;
		      }
		}		
		return check;		
	}
}
