package com.exelatech.ecxapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exelatech.ecxapi.mapper.KaiserFeedbackReportMapper;

@SpringBootApplication
public class Application implements CommandLineRunner{
	
	@Autowired
	private KaiserFeedbackReportMapper kaiserFeedbackReportMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		System.out.println(kaiserFeedbackReportMapper.getDetailsUsingDate("100092021"));
		
	}
}
