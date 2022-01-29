package com.exelatech.ecxapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.LookupConfigMapper;
import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.Lookups;
import com.exelatech.ecxapi.model.Role;
import com.exelatech.ecxapi.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnonymousApiControllerTest {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LookupConfigMapper lookupConfigMapper;

	@Test
	@Order(1)
	public void addUser() {

		// request
		Set<Role> rolelist = new HashSet<Role>();
		Role role = new Role();
		User user = new User();

		role.setRolename("ROLE_ECX_ADMIN");
		rolelist.add(role);
		role = new Role();
		role.setRolename("ROLE_ECX_SUPER_ADMIN");
		rolelist.add(role);

		user.setUsername("Sri4rr4");
		user.setFirstName("Sri4rr4");
		user.setLastName("SrinivasSrirr44");
		user.setNotes("I am anonymous user");
		
		user.setEmail("Sri44@exelaonddddline.com");
		user.setRoles(rolelist);

		int count1 = 0, count2 = 0;
		count1 = userMapper.insertAnonymousRegister(user);
		count2 = userMapper.addUserRoles(user);

		if (count1 != 0 && count2 != 0) {
			assert (true);
		} else {
			assert (false);
		}
	}
	
	@Test	
	@Order(2)   
	public void listUserTest() {
		String type = "PRINT_PLATFORM_MAP";
		List<Lookups> looks = lookupConfigMapper.getAllLookupsByType(type);
		List<String> list = new ArrayList<>();
		for(Lookups lookup : looks) {
			list.add(lookup.getValue());
		}
		assertNotNull(list);
	}

}
