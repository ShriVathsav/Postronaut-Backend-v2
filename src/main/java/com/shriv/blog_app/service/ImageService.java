package com.shriv.blog_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Image;

public interface ImageService {

	public Image createImage(String imageUrl);
	
	public ResponseEntity<?> deleteImage(Long imageId);
	
	public ResponseEntity<?> deleteImages(List<Long> imageIds);
}
