package com.exelatech.ecx.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class EcxBackendApplicationTests {

	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	// @Autowired
	// RestHighLevelClient client;
	// @Autowired
	// ElasticsearchRestTemplate template;
	
	@Autowired
	JmsTemplate jmsTemplate;

	/*@Test
	void elasticsearchTests() {
		 Person person1 = new Person("TestName2", 40);
		person1 = personRepo.save(person1);
		 
		log.info("Person saved with ID: "+ person1.getId());

		Iterable<Person> persons = personRepo.findAll();
		for(Person person : persons){
			//log.info(person.toString());
			System.out.println("Person ==> "+ person.toString());
		}
		//assertNotNull(persons);
	}*/

	@Test
	void jmsTests() {
		log.info("Sending a text message.");
		jmsTemplate.convertAndSend("testq", "Hello World!!!");
		assertNotNull(jmsTemplate);
	}
}