package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
