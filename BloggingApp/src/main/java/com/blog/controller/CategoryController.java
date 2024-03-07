package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryServiceImpl categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto catDto){
		CategoryDto categDto = this.categoryService.createCategory(catDto);
		return new ResponseEntity<CategoryDto>(categDto, HttpStatus.CREATED);
	}
	
	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto catDto, @PathVariable Integer catId){
		CategoryDto updatedCategDto = this.categoryService.updateCategoryById(catDto, catId);
		
		return new ResponseEntity<CategoryDto>(updatedCategDto, HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		this.categoryService.deleteCategory(catId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category is deleted", true), HttpStatus.OK);
	}
	
	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		CategoryDto categDto = this.categoryService.getCategoryById(catId);
		
		return new ResponseEntity<CategoryDto>(categDto, HttpStatus.OK);
	}
	
	// getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> categDtoList = this.categoryService.getAllCategories();
		
		return new ResponseEntity<List<CategoryDto>>(categDtoList, HttpStatus.OK);
	}
}
