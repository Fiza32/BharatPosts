package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blog.exceptions.DuplicateDataFoundException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.model.Category;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepo categoryRepo;
	private final ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		if(categoryRepo.findByTitle(categoryDto.getTitle()).isPresent()) {
			LOGGER.info("Category already exists with the title: {}", categoryDto.getTitle());
			
			throw new DuplicateDataFoundException("Category already exists with the title: " + categoryDto.getTitle());
		}
		
		Category category = convertToEntity(categoryDto);
		Category savedCategory = categoryRepo.save(category);
		
		LOGGER.info("Category saved with title: {}", categoryDto.getTitle());
		return convertToDto(savedCategory);
	}
	
	@Override
	public CategoryDto getCategoryById(Integer catId) {
		Category category = categoryRepo.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with Id " + catId + " doesn't exists"));
		
		return convertToDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();
		
		return categories
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
	Category category = categoryRepo.findById(catId)
		.orElseThrow(() -> {
			LOGGER.error("Category not found with id: {}", catId);
			return new ResourceNotFoundException("Category not found with id: " + catId);
		});
		
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory = categoryRepo.save(category);
		
		LOGGER.info("Category updated with id: {}", catId);
		return convertToDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer catId) {
		Category category = categoryRepo.findById(catId)
			.orElseThrow(() -> {
				LOGGER.error("Category not found with id: {}", catId);
				return new ResourceNotFoundException("Category not found with id: " + catId);
			});
		
		categoryRepo.delete(category);
		LOGGER.info("Category deleted with id: {}", catId);
	}
	
	private CategoryDto convertToDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}
	
	private Category convertToEntity(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}
}
