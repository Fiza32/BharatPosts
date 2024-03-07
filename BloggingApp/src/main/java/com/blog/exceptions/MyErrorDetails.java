package com.blog.exceptions;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyErrorDetails {

	private LocalDateTime timestamp;
	private String message;
	private String details;
}
