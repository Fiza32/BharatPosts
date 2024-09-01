package com.blog.services;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	
	private final PostRepo postRepo;
	private final UserRepo userRepo;
	private final CategoryRepo categoryRepo;
	private final ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		LOGGER.info("Creating post with title: {}", postDto.getTitle());
		
		User user = userRepo.findById(userId)
				.orElseThrow(() -> {
					LOGGER.error("User with id {} does not exist", userId);
					
					return new ResourceNotFoundException("User with id " + userId + " does not exists");
				});
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> {
					LOGGER.error("Category with id {} does not exist", categoryId);
					
					return new ResourceNotFoundException("Category with id " + categoryId + " does not exists");
				});
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setPublishedDate(LocalDateTime.now());
		
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost =  postRepo.save(post);
		LOGGER.info("Post created successfully with id: {}", newPost.getId());
		
		return convertToDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> {
					LOGGER.error("Post with id {} does not exists", postId);
					return new ResourceNotFoundException("Post with id " + postId + " does not exists");
				});
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepo.save(post);
		LOGGER.info("Post updated successfully with id: {}", postId);
		
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> {
					LOGGER.error("Post with id {} does not exists", postId);
					return new ResourceNotFoundException("Post with id " + postId + " does not exists");
				});
		
		return convertToDto(post);
	}

	@Override
	public PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		
		Page<Post> postPage = postRepo.findAll(pageable);
		
		List<Post> posts = postPage.getContent();
		
		List<PostDto> postDtos = posts
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return createPostResponse(postDtos, postPage);
	}
	
	public PostResponse createPostResponse(List<PostDto> postDtos, Page<Post> postPage) {
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> {
					LOGGER.info("Category with id {} does not exists", categoryId);
					return new ResourceNotFoundException("Category with id " + categoryId + " does not exists");
				});
		
		List<Post> posts = postRepo.findAllByCategory(category);
		
		return posts
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> {
					LOGGER.error("User with id {} does not exists", userId);
					return new ResourceNotFoundException("User with id " + userId + " does not exists");
				});
		
		List<Post> posts = postRepo.findAllByUser(user);
		
		return posts
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	@Override
	public void deletePost(Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> {
					LOGGER.error("Post with id {} does not exists", postId);
					return new ResourceNotFoundException("Post with id " + postId + " does not exists");
				});
		
		postRepo.delete(post);
		LOGGER.info("Post successfully deleted with id: {}", postId);
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = this.postRepo.findAllByTitleContainingIgnoreCase(keyword);
		
		return posts
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private PostDto convertToDto(Post post) {
		return modelMapper.map(post, PostDto.class);
	}
}
