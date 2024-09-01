package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	private int id;
	
	@NotBlank(message = "Comment can't be empty")
	@Size(min = 5, message = "Comment should be at least 5 characters long")
	private String content;
}
