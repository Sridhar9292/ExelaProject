package com.exelatech.ecxapi.controller;

import static com.exelatech.ecxapi.util.Constants.ERROR_SIGNATURE_VALIDATION_FAILED;
import static com.exelatech.ecxapi.util.Constants.ERROR_SUBJECT_TOKEN_VALIDATION_FAILED;
import static com.exelatech.ecxapi.util.Constants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.exelatech.ecxapi.config.TokenProvider;
import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.AuthToken;
import com.exelatech.ecxapi.model.LoginUser;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.UserAuthenticateException;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
	private UserMapper userMapper;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthToken> register(@RequestBody LoginUser loginUser) throws UserAuthenticateException {
    	
  try {
	  boolean isAccountPresent = userMapper.appUserExists(loginUser.getUsername());
	  boolean isAccountEnabled = userMapper.userAccEnableCheck(loginUser.getUsername());
	  if(!isAccountPresent) {
		  throw new UserAuthenticateException(Constants.NOT_EXIST);
	  }
	  if(isAccountPresent && !isAccountEnabled) {
		  throw new UserAuthenticateException(Constants.ACCT_ENABLE_ERROR);
	  }
	  String loginUserName = null;
	  if( loginUser.getUsername().length()>20) {
			loginUserName = loginUser.getUsername().substring(0, 20);
		}else {
			loginUserName = loginUser.getUsername();
		}
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginUserName,
                        loginUser.getPassword()
                )
        );
        log.debug(authentication.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token =null;

          token = tokenProvider.generateToken(authentication);
      
        return ResponseEntity.ok(new AuthToken(token));
 
  }catch(final UserAuthenticateException ue) {
	  throw new UserAuthenticateException(ue.getMessage());
  } catch (final InternalAuthenticationServiceException e1) {
	    e1.printStackTrace();
		throw new UserAuthenticateException(Constants.LDAP_ERROR);
	
  } catch (final Exception e) {
	    e.printStackTrace();
		throw new UserAuthenticateException(Constants.AUTH_ERROR);
	}
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<?> refresh(@RequestHeader("Authorization") String authorization) throws UserAuthenticateException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String reqtoken = authorization.replace(TOKEN_PREFIX,"");
        try{
            if(!tokenProvider.validateToken(reqtoken, tokenProvider.getUsernameFromToken(reqtoken))){
                log.error("token validation failed");
                throw new ResponseStatusException (HttpStatus.UNPROCESSABLE_ENTITY, ERROR_SUBJECT_TOKEN_VALIDATION_FAILED);
            }
        }catch(SignatureException signatureException) {
            log.error(signatureException.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ERROR_SIGNATURE_VALIDATION_FAILED, signatureException);
        }catch(MalformedJwtException malformedJwtException){
            log.error(malformedJwtException.getMessage());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ERROR_SIGNATURE_VALIDATION_FAILED,malformedJwtException);
        }catch (Exception e) {
        	throw new UserAuthenticateException(Constants.ERROR_SUBJECT_TOKEN_VALIDATION_FAILED);
		}
        
        final String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

}
