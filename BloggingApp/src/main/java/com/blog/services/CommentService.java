package com.blog.services;

import com.blog.exceptions.PostNotFoundException;
import com.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId) throws PostNotFoundException;
	void deleteComment(Integer commentId) throws PostNotFoundException;
}
