package com.exelatech.ecxapi.config;

import static com.exelatech.ecxapi.util.Constants.AUTHORITIES_KEY;
import static com.exelatech.ecxapi.util.Constants.LANDING_PAGE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.exelatech.ecxapi.mapper.UserMapper;
import com.exelatech.ecxapi.model.User;
import com.exelatech.ecxapi.util.Constants;
import com.exelatech.ecxapi.util.UserAuthenticateException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenProvider implements Serializable {
	
	@Autowired
	private UserMapper userMapper;

    private static final long serialVersionUID = -3340094763611687912L;
    @Value("${jwt.base64-secret}")
    public String signingKey;
    @Value("${jwt.token-validity-in-seconds}")
    public long accessTokenValiditySeconds;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Authentication authentication) throws UserAuthenticateException {
       
    
    	String permissionCheck=authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
          if(permissionCheck.isEmpty()||permissionCheck==null||permissionCheck.equals("")) {
        	  throw new UserAuthenticateException(Constants.AUTH_INVALID_USER);
          }
    	
    	final String authorities = addPermission(permissionCheck);
    	
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, signingKey.getBytes())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValiditySeconds*1000))
                .claim(LANDING_PAGE, userMapper.getLandingPage(authentication.getName()))
                .compact();
    }

	
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token));
    }

    public boolean validateToken(String token, String principal) {
        final String username = getUsernameFromToken(token);
        return (
              username.equals(principal)
                    && !isTokenExpired(token));
    }

    UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        final JwtParser jwtParser = Jwts.parser().setSigningKey(signingKey.getBytes());

        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
    
    public static String  addPermission(String permissionStr) {
	//System.out.println("before  permissionStr===>"+permissionStr);	
		String[] ConfigPermissionList =null;
		String[] permissionlist = permissionStr.split(",");
		Set<String> finalPermission = new HashSet<String>();
		for(String listValue : permissionlist) {
			//admin
			if(listValue.contains(Constants.ADMIN_VIEW)) {			
				ConfigPermissionList = Constants.ADMIN_VIEW_PERMISSION.split(",");
			}else if(listValue.contains(Constants.ADMIN_ADD)) {
				ConfigPermissionList = (Constants.ADMIN_ADD_PERMISSION+","+Constants.ADMIN_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.ADMIN_EDIT)) {
				ConfigPermissionList = (Constants.ADMIN_EDIT_PERMISSION+","+Constants.ADMIN_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.ADMIN_DELETE)) {
				ConfigPermissionList = (Constants.ADMIN_DELETE_PERMISSION+","+Constants.ADMIN_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.ADMIN_MANAGE)) {
				ConfigPermissionList = (Constants.ADMIN_MANAGE_PERMISSION).split(",");
				//PRINT
			}else if(listValue.contains(Constants.PRINT_VIEW)) {
				ConfigPermissionList = (Constants.PRINT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.PRINT_SENT)) {
				ConfigPermissionList = (Constants.PRINT_SEND_PERMISSION+","+Constants.PRINT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.PRINT_UPDATE)) {
				ConfigPermissionList = (Constants.PRINT_UPDATE_PERMISSION+","+Constants.PRINT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.PRINT_MANAGE)) {
				ConfigPermissionList = (Constants.PRINT_MANAGE_PERMISSION).split(",");
			//PRINTHUB
				
			}else if(listValue.contains(Constants.PRINTHUB_VIEW)) {
				ConfigPermissionList = (Constants.PRINTHUB_VIEW_PERMISSION).split(",");	
			}else if(listValue.contains(Constants.PRINTHUB_MANAGE)) {
				ConfigPermissionList = (Constants.PRINTHUB_MANAGE_PERMISSION).split(",");

			//REPORT
			}else if(listValue.contains(Constants.REPORT_VIEW)) {
				ConfigPermissionList = (Constants.REPORT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REPORT_MANAGE)) {
				ConfigPermissionList = (Constants.REPORT_MANAGE_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REPORT_GENERATE)) {
				ConfigPermissionList = (Constants.REPORT_GENERATE_PERMISSION+","+Constants.REPORT_VIEW_PERMISSION).split(",");
		    //EMAIL
			}else if(listValue.contains(Constants.EMAIL_VIEW)) {	 
				ConfigPermissionList = Constants.EMAIL_VIEW_PERMISSION.split(",");
			}else if(listValue.contains(Constants.EMAIL_ADD)) {
				ConfigPermissionList = (Constants.EMAIL_ADD_PERMISSION+","+Constants.EMAIL_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.EMAIL_EDIT)) {
				ConfigPermissionList = (Constants.EMAIL_EDIT_PERMISSION+","+Constants.EMAIL_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.EMAIL_DELETE)) {
				ConfigPermissionList = (Constants.EMAIL_DELETE_PERMISSION+","+Constants.EMAIL_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.EMAIL_MANAGE)) {
				ConfigPermissionList = (Constants.EMAIL_MANAGE_PERMISSION).split(",");
			//REMIT
			}else if(listValue.contains(Constants.REMIT_VIEW)) {
				ConfigPermissionList = (Constants.REMIT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REMIT_ADD)) {
				ConfigPermissionList = (Constants.REMIT_ADD_PERMISSION+","+Constants.REMIT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REMIT_EDIT)) {
				ConfigPermissionList = (Constants.REMIT_EDIT_PERMISSION+","+Constants.REMIT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REMIT_DELETE)) {
				ConfigPermissionList = (Constants.REMIT_DELETE_PERMISSION+","+Constants.REMIT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REMIT_COMMENT)) {
				ConfigPermissionList = (Constants.REMIT_COMMENT_PERMISSION+","+Constants.REMIT_VIEW_PERMISSION).split(",");
			}else if(listValue.contains(Constants.REMIT_MANAGE)) {
				ConfigPermissionList = (Constants.REMIT_MANAGE_PERMISSION).split(",");
			//ADD DEFULT PERMISSION 
			}else if(listValue.contains(Constants.ADD)){				
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.ADD, Constants.VIEW)};
			}else if(listValue.contains(Constants.EXPORT)){				
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.EXPORT, Constants.VIEW)};
			} else if(listValue.contains(Constants.EDIT)){
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.EDIT, Constants.VIEW)};
			}else if(listValue.contains(Constants.DELETE)) {
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.DELETE, Constants.VIEW)};
			}else if(listValue.contains(Constants.GENERATE)) {
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.GENERATE, Constants.VIEW)};
			}else if(listValue.contains(Constants.SENT)) {
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.SENT, Constants.VIEW)};
			}else if(listValue.contains(Constants.UPDATE)) {
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.UPDATE , Constants.VIEW)};	
			}else if(listValue.contains(Constants.COMMENT)) {
				ConfigPermissionList= new String [] {listValue,listValue.replace(Constants.COMMENT, Constants.VIEW)};	
			}else {
				ConfigPermissionList= new String [] {listValue};
			}
			Collections.addAll(finalPermission, ConfigPermissionList);
		}
		
		//System.out.println("After  permissionStr===>"+finalPermission.stream().collect(Collectors.joining(",")));	
		
		return finalPermission.stream()
				.collect(Collectors.joining(","));
	}
}
