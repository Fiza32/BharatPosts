package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private int id;
	
	@NotBlank(message = "Comment can't be empty")
	private String content;
}
