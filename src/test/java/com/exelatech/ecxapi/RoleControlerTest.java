package com.exelatech.ecxapi;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exelatech.ecxapi.mapper.RoleMapper;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.model.Role;
import com.exelatech.ecxapi.util.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleControlerTest {
	@Autowired
	private RoleMapper roleMapper;

	@Test
	@Order(1) 
	public void roleDetails() { 
		List<Role> roleList =null;
		roleList=roleMapper.getAll();
		assertNotNull(roleList);
	}


	@Test
	@Order(2) 
	public void  addRole(){
		int count1=0; int count2=0;
		Set<Permission> permissionlist =new HashSet<Permission>();
		Permission permission=new Permission();
		permission.setPermissionName("Testpermission2");
		permission.setDescription("Junit Testing2");
		permissionlist.add(permission);
		Role role=new Role();
		role.setRolename("TEST_API_ROLE2");
		role.setDescription("Test API Description");
		role.setPermissions(permissionlist);		
		count1=roleMapper.insert(role);
		count2=roleMapper.insertPermissionsForRole(role);
		if(count1!=0 && count2!=0) {
			assert(true);			
		}else {
			assert(false);
		}

	}
	@Test
	@Order(3) 
	public void updateRole() {	
		int count1=0; int count2=0;
		Set<Permission> permissionlist =new HashSet<Permission>();
		Permission permission=new Permission();
		permission.setPermissionName("Testpermission2");
		permission.setDescription("Junit update Testing");
		permissionlist.add(permission);
		Role role=new Role();
		role.setRolename("TEST_API_ROLE2");
		role.setDescription("Test API Description");
		role.setPermissions(permissionlist);

		count1 =roleMapper.update(role);
		Set<Permission> permissions =role.getPermissions();
		if(permissions !=null && permissions.size()>0) {
			roleMapper.deletePermissionsForRole(role.getRolename());
			count2=roleMapper.insertPermissionsForRole(role);
		}
		if(count1!=0 && count2!=0) {
			assert(true);			
		}else {
			assert(false);
		}
	}
	
	@Test
	@Order(4) 
	public void getRoleDetails() throws NotFoundException { 
		Role role =null;
		role=roleMapper.get("TEST_API_ROLE2");
		assertNotNull(role);
	}
	@Test
	@Order(5) 
	public void serachRoleDetails() { 
		List<Role> serachroleList =null;	
		String id="TEST_API_ROLE2";
		serachroleList=roleMapper.search("%" + id + "%"); 	
		assertNotNull(serachroleList);
	}
	@Test
	@Order(6) 
	public void deleteRole() {	
		int count1=0; int count2=0;
		String id="TEST_API_ROLE2";	
		count1=roleMapper.remove(id);
		count2=roleMapper.deletePermissionsForRole(id);
		if(count1!=0 && count2!=0) {
			assert(true);			
		}else {
			assert(false);
		}
	}
}
