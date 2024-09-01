package com.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.exceptions.CategoryNotFoundException;
import com.blog.model.Category;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(Integer catId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(catId).orElseThrow(() -> new CategoryNotFoundException("Category with Id " + catId + " doesn't exists"));
		
		CategoryDto catDto = this.modelMapper.map(cat, CategoryDto.class);
		
		return catDto;
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = this.categoryRepo.findAll();
		
		List<CategoryDto> catDtos = categories.stream().map(cat -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

	@Override
	public CategoryDto updateCategoryById(CategoryDto categoryDto, Integer catId) {
		// TODO Auto-generated method stub
Category cat = this.categoryRepo.findById(catId).orElseThrow(() -> new CategoryNotFoundException("Category with Id " + catId + " doesn't exists"));
		
		cat.setTitle(categoryDto.getCategoryTitle());
		cat.setDescription(categoryDto.getCategoryDescription());
		
		Category updatedCat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer catId) {
		// TODO Auto-generated method stub
		Category cat = this.categoryRepo.findById(catId).orElseThrow(() -> new CategoryNotFoundException("Category with Id " + catId + " doesn't exists"));
		
		this.categoryRepo.delete(cat);
		
		
	}

}
