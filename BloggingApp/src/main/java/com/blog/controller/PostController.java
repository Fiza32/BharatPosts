package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.config.AppConstants;
import com.blog.exceptions.UserNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) throws UserNotFoundException{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) throws UserNotFoundException{
		List<PostDto> posts = this.postService.getAllPostsByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) throws UserNotFoundException{
		List<PostDto> posts = this.postService.getAllPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIR, required = false) String sortDirection){
		PostResponse posts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
		
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) throws UserNotFoundException{
		PostDto postDto = this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) throws UserNotFoundException{
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) throws UserNotFoundException {
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted", true), HttpStatus.OK);
	}
	
	// Search Post by a keyword in Title
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		List<PostDto> res = this.postService.searchPost(keyword);
		
		return new ResponseEntity<List<PostDto>>(res, HttpStatus.OK);
		
	}
	
	// Post Image Upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException, UserNotFoundException{
		PostDto postDto = this.postService.getPostById(postId);
		  
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImagename(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		
	}
	
	// Method to serve Files
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
