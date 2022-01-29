package com.exelatech.ecxapi.config;

import static com.exelatech.ecxapi.util.Constants.HEADER_STRING;
import static com.exelatech.ecxapi.util.Constants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exelatech.ecxapi.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                log.error("an error occured during getting username from token", e);
                response.setHeader("ECX-Error-Message", "An error occured during getting username from token");
                response.setHeader("ECX-Actual-Error-Message", e.getMessage());
            } catch (ExpiredJwtException e) {
                log.warn("The token is expired and not valid anymore", e);
                response.setHeader("ECX-Error-Message", "the token is expired and not valid anymore");
                response.setHeader("ECX-Actual-Error-Message", e.getMessage());
            } catch(SignatureException e){
                log.error("Authentication Failed. Username or Password is not valid.");
                response.setHeader("ECX-Error-Message", "Authentication Failed. Username or Password is not valid.");
                response.setHeader("ECX-Actual-Error-Message", e.getMessage());
            }
        } else {
            log.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userMapper.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails).booleanValue()) {
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}