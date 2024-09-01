package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.Category;
import com.blog.model.Post;
import com.blog.model.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

    // Find posts by user
    List<Post> findAllByUser(User user);

    // Find posts by category
    List<Post> findAllByCategory(Category category);

    // Find posts where the title contains the given string
    List<Post> findAllByTitleContainingIgnoreCase(String title);
}
