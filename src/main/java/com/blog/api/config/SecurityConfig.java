package com.blog.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.api.security.CustomerDetailService;
import com.blog.api.security.JwtAuthenticationEntryPoint;
import com.blog.api.security.JwtAuthenticationFilter;

import jakarta.transaction.Transactional;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS = {
			"/api/auth/**",
			"/swagger-ui/**", 
			"/v3/api-docs/**", 
			"/v2/api-docs/**",
			"/swagger-resources/**", 
			"/webjars/**"
	};
	
	@Autowired
	private CustomerDetailService customerDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.requestMatchers(PUBLIC_URLS).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		//.httpBasic();
		.exceptionHandling()
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		http.authenticationProvider(authenticationProvider());
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	

//	@Transactional
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		 String username = authentication.getName();
//		 String password = authentication.getCredentials().toString();
//
//	        UserDetails userDetails = customerDetailService.loadUserByUsername(username);
//
//	        if (!passwordEncoder().matches(password, userDetails.getPassword())) {
//	            throw new BadCredentialsException("Invalid credentials");
//	        }
//
//	        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customerDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
}
