package com.exelatech.ecxapi.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exelatech.ecxapi.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${ldap.urls}")
	private String ldapUrls;
	@Value("${ldap.base.dn}")
	private String ldapBaseDn;
	@Value("${ldap.user.search}")
	private String ldapSecurityPrincipal;
	@Value("${ldap.password}")
	private String ldapPrincipalPassword;
	@Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

	@Value("${cors.allowed-origins:}")
	private String[] corsAllowedOrigins;
	@Value("${cors.allowed-methods:}")
	private String[] corsAllowedMethods;
	@Value("${cors.allowed-headers:}")
	private String[] corsAllowedHeaders;
	@Value("${cors.exposed-headers:}")
	private String[] corsExposedHeaders;
	@Value("${cors.max-age}")
	private int corsMaxAge;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests()
                .antMatchers("/printhub/dashbord","/registerUser", "/getAnonymousRole", "/authenticate", "/index.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**","/actuator/**", "/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedOrigins(corsAllowedOrigins)
					.allowedMethods(corsAllowedMethods)
					.allowedHeaders(corsAllowedHeaders)
					.exposedHeaders(corsExposedHeaders)
					.maxAge(corsMaxAge);
			}
		};
    }
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(ldapAuthProvider());
	}

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {
		DefaultSpringSecurityContextSource defaultSpringSecurityContextSource = new DefaultSpringSecurityContextSource(ldapUrls); 
		defaultSpringSecurityContextSource.setUserDn(ldapBaseDn);
		defaultSpringSecurityContextSource.setPassword(ldapPrincipalPassword);
		return  defaultSpringSecurityContextSource;
	}

	@Bean
	public FilterBasedLdapUserSearch ldapSearch(){
		FilterBasedLdapUserSearch filterBasedLdapUserSearch = new FilterBasedLdapUserSearch(ldapSecurityPrincipal, ldapUserDnPattern, contextSource());
		filterBasedLdapUserSearch.setSearchSubtree(true);
		return filterBasedLdapUserSearch;
	} 

	@Bean
	public LdapAuthenticationProvider ldapAuthProvider() {
		LdapAuthenticationProvider ldapAuthenticationProvider = new LdapAuthenticationProvider(bindAuthenticator());
		ldapAuthenticationProvider.setUserDetailsContextMapper(userDetailsContextMapper());
		return ldapAuthenticationProvider;
	}

	@Bean
	public LdapTemplate ldapTemplate() {
	    return new LdapTemplate(contextSource());
	}
	
    @Bean
	public BindAuthenticator bindAuthenticator(){
		BindAuthenticator bindAuthenticator = new BindAuthenticator(contextSource());
		bindAuthenticator.setUserSearch(ldapSearch());
		return bindAuthenticator;
	}
	
	@Bean
    public UserDetailsContextMapper userDetailsContextMapper() {
        return new LdapUserDetailsMapper() {
			@Autowired
			private UserMapper userMapper;

            @Override
            public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
				return userMapper.loadUserByUsername(username);
            }
        };
    }
}
