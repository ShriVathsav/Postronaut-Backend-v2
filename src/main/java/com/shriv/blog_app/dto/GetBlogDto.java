package com.shriv.blog_app.dto;

import java.util.List;

import com.shriv.blog_app.model.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GetBlogDto {

	private Blog blog;
	
	private List<ImageDto> uploadedImagesList;
	
	private List<ImageDto> deletedImagesList;

}
