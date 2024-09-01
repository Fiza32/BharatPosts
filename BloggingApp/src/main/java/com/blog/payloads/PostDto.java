package com.blog.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer id;
	
	@NotBlank(message = "Title is mandatory")
	@Size(min = 10, message = "Title should be at least 10 characters long")
	private String title;
	
	@NotBlank(message = "Content is mandatory")
	@Size(min = 50, message = "Content should be at least 50 characters long")
	private String content;
	
	private String imagename;
	
	@NotNull(message = "Publish date cannot be null")
	private LocalDateTime publishedDate;
	
	private CategoryDto categoryDto;
	private UserDTO userDto;
	
	private final Set<CommentDto> comments = new HashSet<>();
}
 