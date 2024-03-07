package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.PostNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) throws PostNotFoundException{
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/post/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) throws PostNotFoundException{
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleted", true), HttpStatus.OK);
	}
}
