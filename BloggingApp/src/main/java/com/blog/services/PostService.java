package com.blog.services;

import java.util.List;

import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	// Create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) throws UserNotFoundException;
	
	// Update
	PostDto updatePost(PostDto postDto, Integer postId) throws UserNotFoundException;
	
	// Get 
	PostDto getPostById(Integer postId) throws UserNotFoundException;
	
	// Get All Posts
	PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDirection);
	
	// Get All Posts By Category
	List<PostDto> getAllPostsByCategory(Integer categoryId) throws UserNotFoundException;
	
	// Get All Posts By User
	List<PostDto> getAllPostsByUser(Integer userId) throws UserNotFoundException;
	
	// Search in a Post
	List<PostDto> searchPost(String keyword);
	
	// Delete a Post
	void deletePost(Integer postId) throws UserNotFoundException;
}
