package com.exelatech.ecx.backend.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exelatech.ecx.backend.model.Dashboard;
import com.exelatech.ecx.backend.model.ReturnDashboard;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.exelatech.ecx.backend.serviceimpl.ElasticSearchManagerImpl;
import com.exelatech.ecx.backend.statetable.StateTable;
import com.exelatech.ecx.backend.workflow.ExportStateTable;
import com.exelatech.ecx.backend.workflow.ImportStateTable;
import com.exelatech.ecx.backend.workflow.ReceiveStateTable;
import com.exelatech.ecx.backend.workflow.SendStateTable;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {
	
	@Autowired
	RestHighLevelClient resClient;
	
	@Autowired
	ObjectMapper mapper;
	
	@Bean
	public ElasticSearchManager<Dashboard> dashboardService() {
		ElasticSearchManagerImpl<Dashboard> elasticMgrObj = new ElasticSearchManagerImpl<Dashboard>();
		elasticMgrObj.setType("dashboard");
		elasticMgrObj.setIndex("console");
		elasticMgrObj.setClient(resClient);
		elasticMgrObj.setClazz(Dashboard.class);
		elasticMgrObj.setObjectMapper(mapper);
		//elasticMgrObj.s
		return elasticMgrObj;
	}
	
	@Bean
	public ElasticSearchManager<ReturnDashboard> returnDashboardService() {
		ElasticSearchManagerImpl<ReturnDashboard> elasticMgrObj = new ElasticSearchManagerImpl<ReturnDashboard>();
		elasticMgrObj.setType("dashboard");
		elasticMgrObj.setIndex("return-console");
		elasticMgrObj.setClient(resClient);
		elasticMgrObj.setClazz(ReturnDashboard.class);
		elasticMgrObj.setObjectMapper(mapper);
		return elasticMgrObj;
	}
	
	/*
	 * @Bean public ElasticSearchManager<ECXEvent> eventService() {
	 * ElasticSearchManagerImpl<ECXEvent> elasticMgrObj = new
	 * ElasticSearchManagerImpl<ECXEvent>(); elasticMgrObj.setType("event");
	 * elasticMgrObj.setIndex("event-console"); elasticMgrObj.setClient(resClient);
	 * elasticMgrObj.setClazz(ECXEvent.class);
	 * elasticMgrObj.setObjectMapper(mapper); return elasticMgrObj; }
	 */
	/*
	 * @Bean public ElasticSearchManager<ReturnDashboard> eventService() {
	 * ElasticSearchManagerImpl<ReturnDashboard> elasticMgrObj = new
	 * ElasticSearchManagerImpl<ReturnDashboard>();
	 * elasticMgrObj.setType("dashboard");
	 *  elasticMgrObj.setIndex("event-console");
	 * elasticMgrObj.setClient(resClient);
	 * elasticMgrObj.setClazz(ReturnDashboard.class);
	 * elasticMgrObj.setObjectMapper(mapper); return elasticMgrObj; }
	 */
	
	@Bean
	public StateTable receiveStateTable() {
		return ReceiveStateTable.instance();
	}
	
	@Bean
	public StateTable importStateTable() {
		return ImportStateTable.instance();
	}
	
	@Bean
	public StateTable exportStateTable() {
		return ExportStateTable.instance();
	}
	
	@Bean
	public StateTable sendStateTable() {
		return SendStateTable.instance();
	}
	
	

}
