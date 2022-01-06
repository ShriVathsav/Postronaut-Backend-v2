package com.shriv.blog_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	Page<Comment> findByProfileId(Long profileId, Pageable pageable);
	
	Comment findByProfileIdAndBlogId(Long profileId, Long blogId);
	
	Optional<Comment> findByIdAndProfileId(Long id, Long profileId);
	
	Page<Comment> findByBlogId(Long blogId, Pageable pageable);
	
}
