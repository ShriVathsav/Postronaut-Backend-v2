package com.shriv.blog_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BlogContentDto {

	private String contentType;
	
	private String firstContent;
	
	private String secondContent;
	
	private String thirdContent;
	
	private String blob;

}
