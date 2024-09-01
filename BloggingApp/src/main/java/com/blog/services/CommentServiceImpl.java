package com.blog.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final PostRepo postRepo;
	private final CommentRepo commentRepo;
	private final ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId){
		
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> {
					LOGGER.error("Post not found with id: {}", postId);
					return new ResourceNotFoundException("Post not found with id: " + postId);
				});
		
		Comment comment = convertToEnttiy(commentDto);
		comment.setPost(post);
		
		Comment savedComment = commentRepo.save(comment);
		
		LOGGER.info("Comment with id {} successfully added", savedComment.getId());
		return convertToDto(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId){
		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> {
					LOGGER.error("Comment not found with id: {}", commentId);
					return new ResourceNotFoundException("Comment not found with id: " + commentId);
				});
		
		commentRepo.delete(comment);
		LOGGER.info("Comment with id {} successfully deleted", commentId);
	}
	
	private CommentDto convertToDto(Comment comment) {
		return modelMapper.map(comment, CommentDto.class);
	}
	
	private Comment convertToEnttiy(CommentDto commentDto) {
		return modelMapper.map(commentDto, Comment.class);
	}
}
