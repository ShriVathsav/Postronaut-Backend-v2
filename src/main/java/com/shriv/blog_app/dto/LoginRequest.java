package com.shriv.blog_app.dto;

import lombok.Data;

@Data
public class LoginRequest {

	private String password;
	
	private String email;

}
