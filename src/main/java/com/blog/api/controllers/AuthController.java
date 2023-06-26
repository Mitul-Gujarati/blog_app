package com.blog.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.exceptions.ApiException;
import com.blog.api.payloads.JwtAuthRequest;
import com.blog.api.payloads.JwtAuthResponse;
import com.blog.api.payloads.UserDto;
import com.blog.api.security.JwtTokenHelper;
import com.blog.api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetail = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetail);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);

	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUSer(@RequestBody UserDto userDto)
	{
		UserDto registerNewUser = userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);
	}

}
