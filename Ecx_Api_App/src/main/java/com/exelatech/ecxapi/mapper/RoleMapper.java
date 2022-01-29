package com.exelatech.ecxapi.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.model.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends GenericMapper<Role, String> {

	int insertPermissionsForRole(Role role);

	int deletePermissionsForRole(String roleName);
	
	int deleteRolesForUser(String roleName);

	LinkedHashSet<Permission> getAllPermissionsWithSelectionForRole(String roleName);

	List<Role> getAllRolesWithPermissions();
}
