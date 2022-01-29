package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.ErrorEmailListMapper;
import com.exelatech.ecxapi.model.ErrorEmail;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ErrorEmailListControllerTest  {

	@Autowired
	private ErrorEmailListMapper errorEmailListMapper;

	@Test	
	@Order(1)   
	public void getErrorEmailList()  {
		List<ErrorEmail> errorEmail=null;
		try {
			errorEmail=errorEmailListMapper.getAll();
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(errorEmail);
	} 
	@Test	
	@Order(2)   
	public void getErrorEmail( ) {
		String query="william.mcdonalda@transcentra.com";
		List<ErrorEmail> errorEmail=null;
		try {
			errorEmail=errorEmailListMapper.search("%" + query + "%");
		} catch (Exception e) {
			assert(false);
		}
		assertNotNull(errorEmail); ;
	}
	@Test	
	@Order(3)   
	public  void  updateErrorEmail()  {
		ErrorEmail errorEmail=new ErrorEmail();
		errorEmail.setEmailUUID("6ad29e5f-935f-402c-9bb4-0694516eb74b");
		errorEmail.setEmailAddress("TestApi@exelaonline.com");
		int count=0;
		try {
			count = errorEmailListMapper.update(errorEmail);
			if (count != 0 ) {
				assert(true);
			} else {
				assert(false);
			}
		} catch (Exception e) {
			assert(false);
		}
	}



}
