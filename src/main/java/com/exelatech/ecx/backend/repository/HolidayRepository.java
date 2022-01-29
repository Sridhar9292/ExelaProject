package com.exelatech.ecx.backend.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.exelatech.ecx.backend.model.Holiday;

public interface HolidayRepository extends ElasticsearchRepository<Holiday, String>  {

}
