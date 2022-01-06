package com.shriv.blog_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shriv.blog_app.model.Like;
import com.shriv.blog_app.service.LikeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/like")
public class LikeController {

	@Autowired
	private LikeService likeService;
	
	@GetMapping("/{blogId}")
	public Page<Like> getLikesByBlog(@PathVariable Long blogId, Pageable pageable) {
		return likeService.getLikesByBlog(blogId, pageable);
	}
	
	@GetMapping("/byprofile/{profileId}")
	public Page<Like> getLikesByProfile(@PathVariable Long profileId, Pageable pageable) {
		return likeService.getLikesByProfile(profileId, pageable);
	}
	
	@GetMapping("/liked/{blogId}/{profileId}")
	public Like getLikesByBlogAndProfile(@PathVariable(value="blogId") Long blogId, @PathVariable(value="profileId") Long profileId) {
		return likeService.getLikesByBlogAndProfile(blogId, profileId);
	}
	
	@PostMapping("/{blogId}/{profileId}")
	public Like postLike(@PathVariable(value="blogId") Long blogId, @PathVariable(value="profileId") Long profileId) {
		return likeService.postLike(blogId, profileId);
	}
	
	@DeleteMapping("/{blogId}/{profileId}")
	public ResponseEntity<?> deleteLike(@PathVariable(value="blogId") Long blogId, @PathVariable(value="profileId") Long profileId) {
		return likeService.deleteLike(blogId, profileId);
	}
}
