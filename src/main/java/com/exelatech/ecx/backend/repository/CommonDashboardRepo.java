package com.exelatech.ecx.backend.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.exelatech.ecx.backend.model.Dashboard;

public interface CommonDashboardRepo extends  ElasticsearchRepository<Dashboard, String>  {

}
