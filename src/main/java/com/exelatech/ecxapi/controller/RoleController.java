package com.exelatech.ecxapi.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.RoleMapper;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.model.Role;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleMapper roleMapper;

	public RoleController(RoleMapper roleMapper ) {
		this.roleMapper =roleMapper;
	}
	@PreAuthorize("hasAnyAuthority('_administration:role:manage','_administration:role:view')")
	@Operation(summary= "Return all Roles")
	@GetMapping
	public List<Role> roleDetails() throws NotFoundException { 
		List<Role> roleList =null;
		try {
			roleList=roleMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return roleList;
	}

	@PreAuthorize("hasAnyAuthority('_administration:role:manage','_administration:role:view')")
	@Operation(summary= "Get role by rolename")
	@GetMapping("/{rolename}")
	public Role getRoleDetails(@PathVariable String rolename) throws NotFoundException { 
		Role role =null;
		try {
			role=roleMapper.get(rolename);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return role;
	}

	@PreAuthorize("hasAnyAuthority('_administration:role:manage','_administration:role:view')")
	@Operation(summary= "Search Role by partial Rolename")
	@GetMapping("search/{rolename}")
	public List<Role> serachRoleDetails(@PathVariable String rolename) throws NotFoundException { 
		List<Role> serachroleList =null;
		try {
			serachroleList=roleMapper.search("%" + rolename+ "%"); 
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return serachroleList;
	}

	@PreAuthorize("hasAnyAuthority('_administration:role:edit','_administration:role:manage')")
	@Operation(summary= "Update Role")
	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> updateRole(@RequestBody Role role) throws NotFoundException {	
		int count1=0; int count2=0;
		try {
			count1 =roleMapper.update(role);
			Set<Permission> permissions =role.getPermissions();
			if(permissions !=null && permissions.size()>0) {
				roleMapper.deletePermissionsForRole(role.getRolename());
				count2 =roleMapper.insertPermissionsForRole(role);
			}
			if(count1!=0 && count2!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("Rolename "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyAuthority('_administration:role:add','_administration:role:manage')")
	@Operation(summary= "Add Role")
	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> addRole(@RequestBody Role role) throws NotFoundException {
		int count1=0; int count2=0;
		try {
			count1=roleMapper.insert(role);
			count2=roleMapper.insertPermissionsForRole(role);
			if(count1!=0 && count2!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				return ResponseEntity.ok().body(role.getRolename() +"--"+ Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("Rolename "+Constants.ALREDY_EXIST);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}
	@PreAuthorize("hasAnyAuthority('_administration:role:delete','_administration:role:manage')")
	@Operation(summary= "Delete Role")
	@DeleteMapping("/{rolename}")
	public ResponseEntity<String> deleteRole(@PathVariable String rolename) throws NotFoundException {	
		int count=0; 
		try {
			roleMapper.deleteRolesForUser(rolename);
			roleMapper.deletePermissionsForRole(rolename);
			count=roleMapper.remove(rolename);
	
			if(count!=0) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("Rolename "+Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}


}
