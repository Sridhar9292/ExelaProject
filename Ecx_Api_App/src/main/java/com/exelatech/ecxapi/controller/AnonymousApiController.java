package com.exelatech.ecxapi.controller;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exelatech.ecxapi.mapper.LookupConfigMapper;
import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.Lookups;
import com.exelatech.ecxapi.model.User;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AnonymousApiController {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private LookupConfigMapper lookupConfigMapper;

	@Autowired
	private LdapTemplate ldapTemplate;
	
	@Value("${ldap.user.customUserSearchBase}")
	private String customUserSearchBase;

	@PostMapping("registerUser")
	public ResponseEntity<String> addUser(@RequestBody User user) throws NotFoundException {
		user.setUsername(user.getFirstName()+"." + user.getLastName());
		String loginUserName = null;
		if( user.getUsername().length()>20) {
			loginUserName = user.getUsername().substring(0, 20);
		}else {
			loginUserName = user.getUsername();
		}
		if (!checkIfLdapUser(loginUserName.toLowerCase())) {
			throw new NotFoundException(Constants.LDAP_USER_ERROR);
		}

		try {
			
			boolean isPresent = userMapper.appUserExists(user.getUsername());
			if (isPresent) {
				throw new NotFoundException("Username " + Constants.ALREDY_EXIST);
			}
			int count1 = userMapper.insertAnonymousRegister(user);
			int count2 = userMapper.addUserRoles(user);

			if (count1 != 0 && count2 != 0) {
				return ResponseEntity.ok().body(Constants.SUCCESS_STATUS);
			} else {
				throw new NotFoundException(Constants.NOT_INSERT);
			}
		} catch (NotFoundException e) {
			throw new NotFoundException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			throw new NotFoundException(e.getMessage());
		}
	}

	@GetMapping("/getAnonymousRole")
	public List<Lookups> getRoles() {
		List<Lookups> looks = lookupConfigMapper.getUserRegisterLookups();
		return looks;
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
