package com.exelatech.ecxapi.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.exelatech.ecxapi.model.Dashboard;

public interface DashboardRepository extends ElasticsearchRepository<Dashboard, String> {

}
