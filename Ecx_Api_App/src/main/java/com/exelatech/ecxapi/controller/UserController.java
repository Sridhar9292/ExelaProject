package com.exelatech.ecxapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.User;
import com.exelatech.ecxapi.model.UserProfile;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tags(value = { @Tag(name = "user", description = "User management") })
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Value("${ldap.user.customUserSearchBase}")
	private String customUserSearchBase;

	@PreAuthorize("hasAnyAuthority('_administration:user:view','_administration:user:manage')")
	@Operation(summary = "Return all users")
	@GetMapping
	public List<User> listUser() throws NotFoundException {
		List<User> user = null;
		try {
			user = userMapper.getAll();
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return user;
	}

	@PreAuthorize("hasAnyAuthority('_administration:user:view','_administration:user:manage')")
	@Operation(summary = "Get user by username")
	@GetMapping(value = "/{username}")
	public User getUserByUsername(@PathVariable(value = "username") String username) throws NotFoundException {
		User user = null;
		try {
			user = userMapper.get(username);
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return user;
	}

	@PreAuthorize("hasAnyAuthority('_administration:user:view','_administration:user:manage')")
	@Operation(summary = "Search user by partial username")
	@GetMapping("/search/{searchTerm}")
	public List<User> searchUserDetails(@PathVariable String searchTerm) throws NotFoundException {
		List<User> user = null;
		try {
			user = userMapper.search("%" + searchTerm + "%");
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return user;
	}

	@PreAuthorize("hasAnyAuthority('_administration:user:edit','_administration:user:manage')")
	@Operation(summary = "Update user")
	@PutMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> updateUser(@RequestBody User user) throws NotFoundException {
		int count1 = 0, count2 = 0;
		try {
			if (user.getVersion() == null) {
				user.setUsername(user.getUsername().toLowerCase());
			}
			count1 = userMapper.update(user);
			if (count1 != 0 && user.getRoleList() != null) {
				userMapper.deleteUserRoles(user.getUsername());
				if (user.getRoleList().size() > 0)
					count2 = userMapper.addUserRoles(user);
			}
			if (count1 != 0 || count2 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Username " + Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:user:add','_administration:user:manage')")
	@Operation(summary = "Add user")
	@PostMapping
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> addUser(@RequestBody User user) throws NotFoundException {
		String loginUserName = null;
		if( user.getUsername().length()>20) {
			loginUserName = user.getUsername().substring(0, 20);
		}else {
			loginUserName = user.getUsername();
		}
		
		if(!checkIfLdapUser(loginUserName.toLowerCase())) {
			throw new NotFoundException(Constants.LDAP_USER_ERROR);
		}
		
		int count1 = 0;

		try {
			if (user.getVersion() == null) {
				// if new user, lowercase userId
				user.setUsername(user.getUsername().toLowerCase());
			}

			if (!StringUtils.isBlank(user.getUsername())) {
				// if (user.getVersion() == null) {
				count1 = userMapper.insert(user);
				if (user.getRoleList() != null && user.getRoleList().size() > 0)
					userMapper.addUserRoles(user);
			}
			if (count1 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException(Constants.NOT_INSERT);
			}
		} catch (DuplicateKeyException duplicateKeyException) {
			throw new NotFoundException("Username " + Constants.ALREDY_EXIST);
		} catch (Exception e) {

			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("hasAnyAuthority('_administration:user:delete','_administration:user:manage')")
	@Operation(summary = "Delete user")
	@DeleteMapping("/{username}")
	@Transactional(rollbackFor = NotFoundException.class)
	public ResponseEntity<String> deleteUser(@PathVariable String username) throws NotFoundException {

		int count2 = 0;
		try {
			userMapper.deleteUserRoles(username);
			count2 = userMapper.remove(username);
			if (count2 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException("Username " + Constants.NOT_EXIST);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/profile/{username}")
	public UserProfile Userprofile(@PathVariable(value = "username") String username) throws NotFoundException {
		User tempUser = null;
		UserProfile userprofile = new UserProfile();
		try {
			tempUser = userMapper.get(username);
			if (tempUser != null) {
				userprofile.setUsername(tempUser.getUsername());
				userprofile.setFirstName(tempUser.getFirstName());
				userprofile.setLastName(tempUser.getLastName());
				userprofile.setEmail(tempUser.getEmail());

				String permissionList = tempUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.joining(","));

				userprofile.setAuth(permissionList);
				userprofile.setLandingPage(tempUser.getLandingPage());
			}

		} catch (final Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
		return userprofile;
	}

	private boolean checkIfLdapUser(String userName) {
		boolean isLdapUser = false;
		String searchFilter = "(sAMAccountName=" + userName + ")";
		SearchControls searchControls = new SearchControls();
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		List<String> list = ldapTemplate.search(customUserSearchBase, searchFilter, searchControls,
				(AttributesMapper<String>) attrs -> (String) attrs.get("cn").get());
		if (list.size() > 0) {
			isLdapUser = true;
		}
		return isLdapUser;
	}

}
