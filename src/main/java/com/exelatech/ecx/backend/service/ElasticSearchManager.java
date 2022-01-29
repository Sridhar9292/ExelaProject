 

package com.exelatech.ecx.backend.service;

import java.io.IOException;

import org.elasticsearch.action.index.IndexResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

//public interface ElasticSearchManager<T> {

	public interface ElasticSearchManager<T> {
		
	T get(String id) throws IOException;
	boolean exists(String id);
	 IndexResponse save(T object) throws JsonProcessingException;
	
	/*
	 * List<T> getAll() throws IOException;
	 * 
	 * List<T> getAll(String field, SortOrder sortOrder) throws IOException;
	 * 
	 * List<T> getAll(Map<String, SortOrder> sortOrderMap) throws IOException;
	 * 
	 * boolean exists(String id);
	 * 
	 * UpdateResponse upsert(T object) throws JsonProcessingException;
	 * 
	 * IndexResponse save(T object) throws JsonProcessingException;
	 * 
	 * IndexResponse save(String index, String type, String key, JSONObject json)
	 * throws JsonProcessingException;
	 * 
	 * boolean remove(String id);
	 * 
	 * boolean remove(String index, String type, String id);
	 * 
	 * List<T> search(String searchTerm, Class clazz);
	 * 
	 * List<T> search(Map<String, String> queryMap) throws IOException;
	 * 
	 * List<T> search(Map<String, String> query, Map<String, SortOrder> sort) throws
	 * IOException;
	 * 
	 * List<SearchResponse> search(List<SearchRequestBuilder> requests);
	 * 
	 * void bulkRequest(T object) throws Exception;
	 * 
	 * SearchRequestBuilder getSearchRequestBuilder();
	 * 
	 * SearchRequestBuilder getSearchRequestBuilder(String index, String type);
	 */
}
