package com.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final FileService fileService;
	private final String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(createPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> posts = this.postService.getAllPostsByUser(userId);
		
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> posts = this.postService.getAllPostsByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize, 
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIR, required = false) String sortDirection
			)
	{
		PostResponse posts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
		
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = postService.getPostById(postId);
		
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}
	
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = postService.updatePost(postDto, postId);
		
		return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		
		return new ResponseEntity<>(new ApiResponse("Post is deleted", true), HttpStatus.OK);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
		List<PostDto> res = this.postService.searchPost(keyword);
		
		return new ResponseEntity<List<PostDto>>(res, HttpStatus.OK);
		
	}
	
	// Upload an image to a Single Post
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
		PostDto postDto = postService.getPostById(postId);
		
		String fileName = fileService.uploadImage(path, image);
		postDto.setImagename(fileName);
		
		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}
	
	// Method to serve Files
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
