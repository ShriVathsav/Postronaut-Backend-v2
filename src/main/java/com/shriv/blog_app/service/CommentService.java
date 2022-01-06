package com.shriv.blog_app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Comment;

public interface CommentService {
	
	public Comment postComment(Comment comment, Long profileId, Long blogId);
	
	public Comment updateComment(Comment comment, Long commentId);
	
	public ResponseEntity<?> deleteComment(Long commentId);
	
	public List<Comment> getAllComments();
	
	public Page<Comment> getCommentsByBlog(Long blogId, Pageable pageable);
	
	public Page<Comment> getCommentsByProfile(Long profileId, Pageable pageable);
	
}
