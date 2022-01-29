package com.exelatech.ecx.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
//@Document(indexName = "holidays")
@Component
public class Holiday {

	@Id
	private String id;
	private String holidayName;
}
