package com.shriv.blog_app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Like;

public interface LikeService {
	
	public Like postLike(Long blogId, Long profileId);
	
	public ResponseEntity<?> deleteLike(Long blogId, Long profileId); 
	
	public Page<Like> getLikesByBlog(Long blogId, Pageable pageable);
	
	public Page<Like> getLikesByProfile(Long profileId, Pageable pageable);
	
	public Like getLikesByBlogAndProfile(Long blogId, Long profileId);
	
}
