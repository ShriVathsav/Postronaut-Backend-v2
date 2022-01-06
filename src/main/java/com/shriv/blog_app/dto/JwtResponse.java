package com.shriv.blog_app.dto;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	
	private String type = "Bearer";
	
	private Long id;
	
	private String username;
	
	private String profilePicUrl;
	
	private String email;
	
	private List<String> roles;
	
	public JwtResponse(String accessToken, Long id, String username, String email, String profilePicUrl, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.profilePicUrl = profilePicUrl;
		this.roles = roles;
	}
}