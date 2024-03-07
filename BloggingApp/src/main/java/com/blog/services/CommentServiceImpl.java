package com.blog.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.PostNotFoundException;
import com.blog.model.Comment;
import com.blog.model.Post;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepo;
import com.blog.repositories.PostRepo;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) throws PostNotFoundException {
		// TODO Auto-generated method stub
		
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id " + postId + " doesn't exists"));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) throws PostNotFoundException {
		// TODO Auto-generated method stub
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new PostNotFoundException("Comment with id " + commentId + " doesn't exists"));
		
		this.commentRepo.delete(comment);
	}

}
