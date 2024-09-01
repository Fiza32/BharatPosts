package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto getCategoryById(Integer catId);
	List<CategoryDto> getAllCategories();
	CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);
	void deleteCategory(Integer catId);
}
