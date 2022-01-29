package com.exelatech.ecxapi.mapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.exelatech.ecxapi.model.LabelValue;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.model.Role;
import com.exelatech.ecxapi.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Mapper
public interface UserMapper extends GenericMapper<User, String> {

  List<User> getAll();

  User get(String username);

  int addUserRoles(User user);

  int deleteUserRoles(String username);

  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

  String getUserPassword(String username);

  LinkedHashSet<Role> getAllRolesWithSelectionForUser(String username);

  LinkedHashSet<Permission> loadPermissionsByUsername(String username);

  Set<LabelValue> getUserFilter(String username);

  Set<Role> getRolesByUser(String username);

  LinkedHashSet<Permission> loadPermissionsByRolename(String rolename);
  
  int insertAnonymousRegister(User user);
  
  String getLandingPage(String username);
  
  boolean appUserExists(String username);
  
  boolean userAccEnableCheck(String username);

}
