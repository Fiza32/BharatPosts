package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	
	@NotBlank(message = "Post title must not be blank")
	@Size(min = 10, max = 50, message = "Post title length should be between 10 and 50")
	private String title;
	
	@NotBlank(message = "Post description must not be blank")
	@Size(min = 50, message = "Post description should be atleast of 50 characters")
	private String content;
	
	private String imagename;
	private Date addedDate;
	private CategoryDto category;
	private UserDTO user;
	
	private Set<CommentDto> comments = new HashSet<>();
}
 