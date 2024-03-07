package com.blog.payloads;

import lombok.Data;

@Data
public class SignInRequest {
	
	private String email;
	private String password;
}
