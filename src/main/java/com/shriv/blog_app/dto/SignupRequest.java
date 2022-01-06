package com.shriv.blog_app.dto;

import java.util.Set;

import lombok.Data;

@Data
public class SignupRequest {

	private String username;
	
	private String password;
	
	private String email;
	
	private String description;
	
	private String createdAt;
	
	private String updatedAt;
	
	private Set<String> role;
}
