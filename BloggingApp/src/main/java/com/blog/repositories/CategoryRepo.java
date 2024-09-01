package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Optional<Category> findByTitle(String title);
}
