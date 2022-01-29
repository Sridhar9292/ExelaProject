package com.exelatech.ecxapi.controller;

import java.util.List;

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

import com.exelatech.ecxapi.mapper.PermissionMapper;
import com.exelatech.ecxapi.model.Permission;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;
@Slf4j	
@RestController
@RequestMapping("/permissions")
public class PermissionController {
	@Autowired
	private PermissionMapper permissionMapper;

	@GetMapping
	public List<Permission> permissionDetails() {
		return permissionMapper.getAll();
	}

	@PreAuthorize("hasAnyAuthority('_administration:permission:manage','_administration:permission:view')")
	@GetMapping("/{id}")
	public Permission getPermissionDetails(@PathVariable String id) throws NotFoundException {
		Permission permission = null;
		try {
			permission = permissionMapper.get(id);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return permission;
	}

	@PreAuthorize("hasAnyAuthority('_administration:permission:manage','_administration:permission:view')")
	@GetMapping("search/{id}")
	public List<Permission> serachPermissionDetails(@PathVariable String id) throws NotFoundException {
		List<Permission> permission = null;
		try {
			permission = permissionMapper.search("%" + id + "%");
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return permission;
	}

	@PreAuthorize("hasAnyAuthority('_administration:permission:manage','_administration:permission:edit')")
	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> updatePermission(@RequestBody Permission permission) throws NotFoundException {
		int count = 0;
		try {
			count = permissionMapper.update(permission);
			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("PermissionName"+ Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:permission:manage','_administration:permission:add')")
	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> addPermission(@RequestBody Permission permission) throws NotFoundException {
		int count = 0;
		try {
			// Permission result = null;
			count = permissionMapper.insert(permission);
			if (count != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException(Constants.NOT_INSERT);
			}
		}catch ( DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("PermissionName"+Constants.ALREDY_EXIST);

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:permission:manage','_administration:permission:delete')")
	@DeleteMapping("/{id}")
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> deletePermission(@PathVariable String id) throws NotFoundException {
		int count = 0;
		try {
			count = permissionMapper.remove(id);
			if(count !=0 ) {
				return  ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			}else {
				throw new NotFoundException("PermissionName"+ Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

}
