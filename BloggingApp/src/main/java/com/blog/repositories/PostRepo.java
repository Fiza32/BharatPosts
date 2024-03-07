package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
