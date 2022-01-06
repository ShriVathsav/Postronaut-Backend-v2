package com.shriv.blog_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.ImageRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Image;
import com.shriv.blog_app.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private ImageRepository imageRepository;

	public ImageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image createImage(String imageUrl) {
		Image image = new Image();
		image.setImageUrl(imageUrl);
		return imageRepository.save(image);
	}

	@Override
	public ResponseEntity<?> deleteImage(Long imageId) {
		return imageRepository.findById(imageId).map(image -> {
            imageRepository.delete(image);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("Comment not found with id " + imageId + " and postId ")
        );
	}

	@Override
	public ResponseEntity<?> deleteImages(List<Long> imageIds) {
		List<Image> images = imageRepository.findAllById(imageIds);
		imageRepository.deleteAll(images);
		return ResponseEntity.ok().build();
	}

}
