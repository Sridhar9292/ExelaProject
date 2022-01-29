package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.KaiserMapper;
import com.exelatech.ecxapi.model.KaiserProcessInfo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KaiserProcessControllerTest {

	@Mapper
	private KaiserMapper kaiserMapper;

	@Test	
	@Order(1)  
	public void getall() { 
		List<KaiserProcessInfo> kaiserProcessInfo=null;
		try {
			kaiserProcessInfo=kaiserMapper.getKaiProcessInfo();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(kaiserProcessInfo);
	}
	@Test	
	@Order(2)  
	public void serach()  { 
		
		 String column ="job";
		 String value="A0X8YX";
		List<KaiserProcessInfo> kaiserProcessInfo=null;
		try {
			if(column.equalsIgnoreCase("job")) {		

				kaiserProcessInfo=kaiserMapper.getKaiProcessInfoSearchJob(value); 
			}else {
				kaiserProcessInfo=kaiserMapper.getKaiProcessInfo();
			}
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(kaiserProcessInfo); 	;
	}

}
