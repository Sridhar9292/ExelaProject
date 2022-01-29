package com.exelatech.ecxapi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.exelatech.ecxapi.model.ReturnDashboard;

public interface ReturnDashboardRepository extends ElasticsearchRepository<ReturnDashboard, String> {

}
