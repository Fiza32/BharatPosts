package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Integer id;
	
	@NotBlank(message = "Title is mandatory")
	@Size(min = 10, message="Title should be at least of 10 characters long")
	private String title;
	
	@NotBlank(message = "Description is mandatory")
	@Size(min = 15, message="Description should be at least of 10 characters long")
	private String description;
}
