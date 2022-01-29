package com.exelatech.ecxapi;

	import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.PermissionMapper;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.util.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PermissionControllerTest {
	@Autowired
	private PermissionMapper permissionMapper;

	
	@Test	
	@Order(1)  
	public void addPermission()  {
		int count = 0;
		Permission permission=new Permission();
		permission.setPermissionName("Testpermission2");
		permission.setDescription("Junit Testing");

		count = permissionMapper.insert(permission);
		if (count != 0) {
			assert(true);
		} else {
			assert(false);
		}

	}
	@Test
	@Order(2)    
	public void permissionDetails() {
		List<Permission> permission= permissionMapper.getAll();	
		assertNotNull(permission);
	}
	
	@Test
	@Order(3) 
	public void updatePermission() throws NotFoundException {
		int count = 0;
		Permission permission=new Permission();
		permission.setPermissionName("Testpermission2");
		permission.setDescription("Junit update Testing");
		count = permissionMapper.update(permission);
		if (count != 0) {
			assert(true);
		} else {
			assert(false);
		}
	}
	
	@Test
	@Order(4)    
	public void getPermissionDetails() throws NotFoundException {
		Permission permission = null;
		permission = permissionMapper.get("Testpermission2");
		assertNotNull(permission);
	}

	@Test
	@Order(5) 
	public void serachPermissionDetails() throws NotFoundException {
		List<Permission> permission = null;
		String id="Testpermission2";
		permission = permissionMapper.search("%" + id + "%");
		assertNotNull(permission);
	}
	@Test
	@Order(6) 
	public void deletePermission(){
		int count = 0;
		count = permissionMapper.remove("Testpermission2");
		if (count != 0) {
			assert(true);
		} else {
			assert(false);
		}

	}

}
