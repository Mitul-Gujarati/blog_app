package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);

	UserDto createUSer(UserDto userDto);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);

}
