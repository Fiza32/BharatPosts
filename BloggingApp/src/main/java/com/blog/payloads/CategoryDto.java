package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotBlank(message = "Category title can't be empty")
	@Size(min = 5, max = 50, message="Title size must be between 5 to 50")
	private String categoryTitle;
	
	@NotBlank(message = "Category Description can't be empty")
	@Size(min = 15, max = 250, message="Description size must be between 15 to 250")
	private String categoryDescription;
}
