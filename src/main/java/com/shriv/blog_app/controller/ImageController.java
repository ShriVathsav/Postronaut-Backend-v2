package com.shriv.blog_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shriv.blog_app.dto.ImageDto;
import com.shriv.blog_app.model.Image;
import com.shriv.blog_app.s3.AmazonClient;
import com.shriv.blog_app.service.ImageService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/image")
public class ImageController {
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private ImageService imageService;
	
	@PostMapping(value= "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Image postImage(@RequestParam MultipartFile file) {
		String fileUrl = amazonClient.uploadFile(file);
		return imageService.createImage(fileUrl);
	}
	
	@DeleteMapping("/delete")
	public String deleteImage(@RequestParam String imageUrl, @RequestParam Long imageId) {
		String message = amazonClient.deleteFileFromS3Bucket(imageUrl);
		imageService.deleteImage(imageId);
		return message;
	}

}
