package com.exelatech.ecx.backend.serviceimpl;

import java.io.IOException;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Component;

import com.exelatech.ecx.backend.repository.CommonDashboardRepo;
import com.exelatech.ecx.backend.service.ElasticSearchManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ElasticSearchManagerImpl<T> implements ElasticSearchManager<T> {

	// protected Client client;
	@Autowired
	protected RestHighLevelClient client;
	protected String index;
	protected String type;
	protected Class<T> clazz;
	protected ObjectMapper objectMapper;
	// static BulkProcessor bulkProcessor = null;
	T t = null;
	@Autowired
	private CommonDashboardRepo repoCommon;
	@Autowired
	ElasticsearchRestTemplate template;
	/*
	 * public ElasticSearchManagerImpl(Class<T> claszz) { this.clazz = claszz; }
	 */

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setClient(RestHighLevelClient client) {
		this.client = client;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public T get(String id) throws IOException {
		T t = null;
		try {
			GetRequest get = new GetRequest(index, id);
			GetResponse resp = client.get(get, RequestOptions.DEFAULT);

			if (resp.isExists()) {
				t = objectMapper.readValue(resp.getSourceAsString(), clazz);
				// System.out.println("get() "+ t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;

		/*
		 * Dashboard db = dashboardRepository.findById(id).get();
		 * System.out.println("Get Dashboard: " + db);
		 * 
		 * System.out.println("Elastic manager ==> "+index+" , "+type+", "+id);
		 * GetResponse response = client. prepareGet(index, type, id).get();
		 * if(response.isExists()) { t =
		 * objectMapper.readValue(response.getSourceAsString(), clazz); }
		 */
	}

	/*
	 * public IndexResponse save(T object) throws JsonProcessingException {
	 * PropertyAccessor myAccessor =
	 * PropertyAccessorFactory.forBeanPropertyAccess(object); String key = (String)
	 * myAccessor.getPropertyValue("id"); IndexResponse response = null; byte[] json
	 * = objectMapper.writeValueAsBytes(object); IndexResponse response =
	 * client.prepareIndex(getWriteIndex(key), type, key).setSource(json,
	 * XContentType.JSON).get(); //IndexRequest indexRequest = new
	 * IndexRequest(index).id(key).source(object);
	 * 
	 * try { response = client.index(indexRequest, RequestOptions.DEFAULT); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * return response; }
	 */
	public boolean exists(String id) {
		GetRequest get = new GetRequest(index, id);
		GetResponse resp = null;
		// try {
		//resp = client.get(get, RequestOptions.DEFAULT);
		return repoCommon.existsById(id);
		// } catch (IOException e) { // TODO Auto-generated
	 // e.printStackTrace();
	//	  }
		// return resp.isExists();
	}

	@Override
	public IndexResponse save(T object) throws JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * public boolean exists(String id) { GetResponse response =
	 * client.prepareGet(index, type, id).get(); return response.isExists(); }
	 */

}
