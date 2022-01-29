package com.exelatech.ecx.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.exelatech.ecx.backend.controller.TransferController;
import com.exelatech.ecx.backend.dao.mapper.HolidayMapper;
import com.exelatech.ecx.backend.repository.HolidayRepository;

@SpringBootApplication
@MapperScan("com.exelatech.ecx.backend.dao.mapper")
@EnableJms
@EnableScheduling
public class EcxBackendApplication implements CommandLineRunner{

	@Autowired
	HolidayRepository holidayRepo;
	
	@Autowired
	HolidayMapper holidayMapper;

	public static void main(String[] args) {
		SpringApplication.run(EcxBackendApplication.class, args);
	}
	
	public EcxBackendApplication() {

	}

	@Override
	public void run(String... args) throws Exception {
		TransferController transfer = new TransferController();
		transfer.Run();
		
	}

}
