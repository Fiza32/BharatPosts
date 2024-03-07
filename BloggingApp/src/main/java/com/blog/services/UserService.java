package com.blog.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDto);
	UserDTO updateUser(UserDTO userDto, Integer userID) throws UserNotFoundException;
	UserDTO getUserById(Integer userID) throws UserNotFoundException;
	List<UserDTO> getAllUsers();
	void deleteUser(Integer userID) throws UserNotFoundException;
	UserDetailsService userDetailsService();
}
