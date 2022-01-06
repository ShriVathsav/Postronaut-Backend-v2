package com.shriv.blog_app.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shriv.blog_app.model.Like;


public interface LikeRepository extends JpaRepository<Like, Long> {
	
	Page<Like> findByProfileId(Long profileId, Pageable pageable);
	
	Page<Like> findByBlogId(Long blogId, Pageable pageable);	
	
	Optional<Like> findByIdAndProfileId(Long likeId, Long profileId);
	
	Optional<Like> findByBlogIdAndProfileId(Long blogId, Long profileId);

}
