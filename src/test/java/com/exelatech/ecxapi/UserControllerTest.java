package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.Role;
import com.exelatech.ecxapi.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
	@Autowired
	UserMapper userMapper;

	@Test	
	@Order(2)   
	public void listUserTest() {
		List<User> userlist = userMapper.getAll();
		assertNotNull(userlist);
	}
	
	@Test
	@Order(1) 
	public void addUser(){

		//request 
		Set<Role> rolelist =new HashSet<Role>();
		Role role =new Role();
		User user=new User();

		role.setRolename("ROLE_ECX_ADMIN");
		role.setDescription("ecx Admin Role");
		rolelist.add(role);
		role =new Role();
		role.setRolename("ROLE_ECX_SUPER_ADMIN");
		role.setDescription("Super Admin role");
		rolelist.add(role);

		user.setUsername("JunitTest4");
		user.setFirstName("API");
		user.setPassword("");
		user.setConfirmPassword("");
		user.setLastName("DEV");
		user.setEmail("TestJunitTest4@exelaonline.com");
		user.setVersion(null);
		user.setRoles(rolelist);
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setAccountLocked(true);
		user.setDeleted(false);
		user.setEntryDate(null);
		user.setModifyDate(null);
		user.setUserAbbrev(null);
		user.setCredentialsExpired(true);


		int count1 = 0, count2 = 0;
		if (user.getVersion() == null) {
			user.setUsername(user.getUsername().toLowerCase());
		}
		if (!StringUtils.isBlank(user.getUsername()) && !"".equals(user.getVersion())) {
			count1 = userMapper.insert(user);
			count2= userMapper.addUserRoles(user);
		}
		if (count1 != 0 && count2 != 0) {
			assert(true);
		} else {
			assert(false);
		}
	}
	@Test
	@Order(3)   
	public void updateUser(){

		//request 
		Set<Role> rolelist =new HashSet<Role>();
		Role role =new Role();
		User user=new User();

		role.setRolename("ROLE_ECX_ADMIN");
		role.setDescription("ecx Admin Role");
		rolelist.add(role);
		role =new Role();
		role.setRolename("ROLE_ECX_SUPER_ADMIN");
		role.setDescription("Super Admin role");
		rolelist.add(role);

		user.setUsername("JunitTest4");
		user.setFirstName("API update");
		user.setPassword("");
		user.setConfirmPassword("");
		user.setLastName("DEV");
		user.setEmail("JunitTest4@exelaonline.com");
		user.setVersion(null);
		user.setRoles(rolelist);
		user.setEnabled(true);
		user.setAccountExpired(false);
		user.setAccountLocked(true);
		user.setDeleted(false);
		user.setEntryDate(null);
		user.setModifyDate(null);
		user.setUserAbbrev(null);
		user.setCredentialsExpired(true);


		int count1=0; int count2 = 0;
		if (user.getPassword() == null) {
			user.setPassword("");
		}
		user.setUsername(user.getUsername().toLowerCase());
		count1 = userMapper.update(user);
		userMapper.deleteUserRoles(user.getUsername());
		count2 = userMapper.addUserRoles(user);
		if (count1 != 0 && count2 != 0) {
			assert(true);
		} else {
			assert(false);
		}
	}
	
	@Test
	@Order(4) 
	public void getUserByUsernameTest() {
		User user = userMapper.get("JunitTest4");
		assertNotNull(user);
	}
	@Test
	@Order(5) 
	public void searchUserDetails() {
		List<User> user = userMapper.search("JunitTest4");
		assertNotNull(user);
	}
	
	@Test
	@Order(6) 
	public void deleteUser() {
		String id="JunitTest4";
		int count1=0; int count2 = 0;
		count1 = userMapper.deleteUserRoles(id.toLowerCase());
		count2 = userMapper.remove(id.toLowerCase());
		if (count1 != 0 && count2 != 0) {
			assert(true);
		} else {
			assert(false);
		}
	}

}