package com.blog.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {
	@NotBlank(message = "Name is required and must be between 4 and 15 characters")
    @Size(min = 4, max = 15)
	private String name;
	
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "About section is required")
	private String about;
	
	@NotBlank(message = "Password is required and must be between 6 and 15 characters")
    @Size(min = 6, max = 15)
	private String password;
	
	private String role;
}