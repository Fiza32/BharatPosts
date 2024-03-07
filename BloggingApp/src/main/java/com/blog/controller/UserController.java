package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDTO;
import com.blog.services.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
	
	
	// POST - CREATE USER
//	@PostMapping("/")
//	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto){
//		UserDTO createdUserDto = this.userService.createUser(userDto);
//		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
//	}
	
	
	// GET - Get All Users
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	// GET - Get a single user
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable Integer userId) throws UserNotFoundException{
		UserDTO user = this.userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	
	// PUT - UPDATE USER
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDto, @PathVariable Integer userId) throws UserNotFoundException{
		UserDTO updatedUser = this.userService.updateUser(userDto, userId);
		
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	// DELETE - DELETE USER
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) throws UserNotFoundException{
		this.userService.deleteUser(userId);
		
		return new ResponseEntity<>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
	}
	
}
