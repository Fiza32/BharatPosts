package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.exceptions.UserNotFoundException;
import com.blog.model.User;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.dtoToEntity(userDto);
		User savedUser = userRepo.save(user);
		return this.entityToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userID) throws UserNotFoundException {
		User user = this.userRepo.findById(userID).orElseThrow(() -> new UserNotFoundException("User with id " + userID + " does not exists"));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.entityToDto(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userID) throws UserNotFoundException {
		User user = this.userRepo.findById(userID).orElseThrow(() -> new UserNotFoundException("User with id " + userID + " does not exists"));
		
		return this.entityToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<UserDTO> userDtoUpdated = users.stream().map(user -> this.entityToDto(user)).collect(Collectors.toList());
		
		
		return userDtoUpdated;
	}

	@Override
	public void deleteUser(Integer userID) throws UserNotFoundException {
		User user = this.userRepo.findById(userID).orElseThrow(() -> new UserNotFoundException("User with id " + userID + " does not exists"));
		
		this.userRepo.delete(user);
	}
	
	public User dtoToEntity(UserDTO userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		User user = this.modelMapper.map(userDto, User.class);
		
		return user;
	}
	
	public UserDTO entityToDto(User user) {
		
		// Let say 1stClass - User, 2ndClass - UserDTO
		// modelMapper.map(1stClass, 2ndClass)
		UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
		
		return userDto;
	}

	@Override
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				return userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email"));
			}
		};
	}
}
