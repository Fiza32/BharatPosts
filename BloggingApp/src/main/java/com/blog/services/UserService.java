package com.blog.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDto);
	UserDTO updateUser(UserDTO userDto, Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userId);
	
	UserDetailsService userDetailsService();
}
