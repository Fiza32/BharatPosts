package com.blog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.DuplicateDataFoundException;
import com.blog.exceptions.UserNotFoundException;
import com.blog.model.User;
import com.blog.payloads.UserDTO;
import com.blog.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepo userRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		Optional<User> existingUser = userRepo.findByEmail(userDto.getEmail());
		if(existingUser.isPresent()) {
			new DuplicateDataFoundException("User already exists with this email: " + userDto.getEmail());
		}
		
		String hashedPass = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(hashedPass);
		
		User newUser = modelMapper.map(userDto, User.class);
		
		User savedUser = userRepo.save(newUser);
		
		return modelMapper.map(savedUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exists"));
		
		User newUser = modelMapper.map(userDto, User.class);
		if(existingUser != null && newUser != null) {
			updateData(existingUser, newUser);
		}
		
		User updatedUser = userRepo.save(existingUser);
		
		return modelMapper.map(updatedUser, UserDTO.class);
	}
	
	public void updateData(User existingUser, User newUser) {
		existingUser.setName(newUser.getName());
		existingUser.setEmail(newUser.getEmail());
		existingUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		existingUser.setAbout(newUser.getAbout());
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exists"));
		
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<UserDTO> updatedUsersDtos = users.stream()
				.map(user -> modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		
		
		return updatedUsersDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exists"));
		
		userRepo.delete(user);
	}

	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				return userRepo.findByEmail(email)
						.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
			}
		};
	}
}
