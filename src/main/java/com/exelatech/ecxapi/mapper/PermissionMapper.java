package com.exelatech.ecxapi.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import com.exelatech.ecxapi.model.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends GenericMapper<Permission, String> {

	List<Permission> getAllDistinct();

	LinkedHashSet<Permission> getPermissionsForRole(String roleName);
}
