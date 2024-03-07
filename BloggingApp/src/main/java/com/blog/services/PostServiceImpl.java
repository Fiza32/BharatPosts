package com.blog.services;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.exceptions.UserNotFoundException;
import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) throws UserNotFoundException {
		
		User user = this.userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exists"));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new UserNotFoundException("Category with id " + categoryId + " does not exists"));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost =  this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new UserNotFoundException("Post with id " + postId + " does not exists"));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImagename());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new UserNotFoundException("Post with id " + postId + " does not exists"));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDirection) {
		
		Sort sort = sortDirection.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
//		if(sortDirection.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} 
//		else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		Pageable pgl = PageRequest.of(pageNum, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pgl);
		
		List<Post> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) throws UserNotFoundException {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new UserNotFoundException("Category with id " + categoryId + " does not exists"));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) throws UserNotFoundException {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " does not exists"));
		
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}


	@Override
	public void deletePost(Integer postId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new UserNotFoundException("Post with id " + postId + " does not exists"));
		
		this.postRepo.delete(post);
	}

	
	@Override
	public List<PostDto> searchPost(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		
		return postDtos;
	}
}
