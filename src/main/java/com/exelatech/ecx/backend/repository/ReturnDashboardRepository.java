package com.exelatech.ecx.backend.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.exelatech.ecx.backend.model.ReturnDashboard;

public interface ReturnDashboardRepository<T> extends ElasticsearchRepository<ReturnDashboard, String>  {

	//T get(String id) throws IOException;
}
