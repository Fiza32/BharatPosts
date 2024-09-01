package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	PostDto updatePost(PostDto postDto, Integer postId);
	PostDto getPostById(Integer postId);
	PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDirection);
	void deletePost(Integer postId);
	
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	List<PostDto> getAllPostsByUser(Integer userId);
	List<PostDto> searchPost(String keyword);
}
