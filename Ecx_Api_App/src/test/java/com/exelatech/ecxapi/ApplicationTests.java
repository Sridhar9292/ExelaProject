package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.Permission;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	UserMapper userMapper;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testCorsWithAnnotation() throws Exception {
		/*
		ResponseEntity<User> entity = this.restTemplate.exchange(RequestEntity.get(uri("/api/users/1")).header(HttpHeaders.ORIGIN, "http://localhost:8090").build(), User.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		assertEquals("http://localhost:8080", entity.getHeaders().getAccessControlAllowOrigin());
		User user = entity.getBody();
		assertEquals("testuser", user.getUsername());
		*/
		ResponseEntity<Permission> entity = this.restTemplate.exchange(
			RequestEntity.get(uri("/api/permissions/test12345"))
			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZW5rYXRhZHVyZ2F2YXJqaHVsYSIsImF1dGgiOiJfcmVtaXQ6cmV0dXJuRGFzaGJvYXJkOm1hbmFnZSxfcHJpbnQ6cHJpbnRXb3JrZmxvdzptYW5hZ2UsX2VtYWlsOmthaXNlclByb2Nlc3NTdW1tYXJ5Om1hbmFnZSxfYWRtaW5pc3RyYXRpb246cGVybWlzc2lvbjptYW5hZ2UsX3ByaW50OmthaXNlUGFwZXJTdW1tYXJ5Om1hbmFnZSxfcHJpbnQ6ZmFybWluZ0RhbGU6bWFuYWdlLF9hZG1pbmlzdHJhdGlvbjp1c2VyOm1hbmFnZSxfYWRtaW5pc3RyYXRpb246dGVtcGxhdGU6bWFuYWdlLF9yZW1pdDpiaWxsZXJNYW5hZ2VtZW50Om1hbmFnZSxfZGFzaGJvYXJkOio6bWFuYWdlLF9wcmludDprYWlzZXJQcm9jZXNzU3VtbWFyeTptYW5hZ2UsX3JlcG9ydHM6c3VtbWFyeVJlcG9ydHM6bWFuYWdlLF9lbWFpbDpzdWJhY2NvdW50RGFzaGJvYXJkOm1hbmFnZSxfcmVtaXQ6cmVtaXREYXNoYm9hcmQ6bWFuYWdlLF9hZG1pbmlzdHJhdGlvbjpyb2xlOm1hbmFnZSxfYWRtaW5pc3RyYXRpb246aG9saWRheTptYW5hZ2UsX2VtYWlsOmVtYWlsRGFzaGJvYXJkOm1hbmFnZSxfYWRtaW5pc3RyYXRpb246ZmlsZTptYW5hZ2UsX3ByaW50OnByaW50RmlsZVN0YXR1czptYW5hZ2UiLCJpYXQiOjE2MTEwNjQ1MDksImV4cCI6MTYxMTA4MjUwOX0.xBrhi6wliYeJ2JzDJUQpbCBHpoLo1xJ8crn7iKYjzTI")
			.build(), Permission.class);
		Permission p1 = entity.getBody();
		log.info(p1.toString());

		assertTrue(true);
	}

	private URI uri(String path) {
		return restTemplate.getRestTemplate().getUriTemplateHandler().expand(path);
	}

	@Test
	public void userDetailsTest(){
		UserDetails ud = userMapper.loadUserByUsername("VenkataDurgavarjhula");
		log.info(ud.getUsername());
		log.info(ud.toString());
		assertNotNull(ud);
	}
}
