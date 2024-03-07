package com.blog.payloads;

import lombok.Data;

@Data
public class RefreshTokenResponse {
	private String currToken;
	private String newToken;
}
