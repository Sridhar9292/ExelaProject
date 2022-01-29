package com.exelatech.ecx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootHawtioApplication {
	
	@GetMapping("/health")
	public String healthCheck(){
		return "OK";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHawtioApplication.class, args);
	}

}
