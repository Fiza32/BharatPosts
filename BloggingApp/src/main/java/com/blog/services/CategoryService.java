package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	// You don't have to explicitly use 'public' modifier
	// In interface all methods are by default public
	
	// create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// get
	CategoryDto getCategoryById(Integer catId);
	
	// getAll
	List<CategoryDto> getAllCategories();
	
	// update
	CategoryDto updateCategoryById(CategoryDto categoryDto, Integer catId);
	
	// delete
	void deleteCategory(Integer catId);
}
