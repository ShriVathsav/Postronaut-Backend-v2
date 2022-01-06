package com.shriv.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shriv.blog_app.model.Comment;
import com.shriv.blog_app.service.CommentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired	
	private CommentService commentService;
	
	@GetMapping("/")
	public List<Comment> getAllComments(){
		return commentService.getAllComments();
	}
	
	@GetMapping("/{blogId}")
	public Page<Comment> getCommentsByBlog(@PathVariable Long blogId, Pageable pageable){
		return commentService.getCommentsByBlog(blogId, pageable);
	}
	
	@GetMapping("/byprofile/{profileId}")
	public Page<Comment> getCommentsByProfile(@PathVariable Long profileId, Pageable pageable){
		return commentService.getCommentsByProfile(profileId, pageable);
	}
	
	@PostMapping("{blogId}/create/{profileId}")
	public Comment postComment(@RequestBody Comment comment, @PathVariable(name="profileId") Long profileId, @PathVariable(name="blogId") Long blogId) {
		return commentService.postComment(comment, profileId, blogId);
	}
	
	@PutMapping("/{commentId}")
	public Comment updateComment(@RequestBody Comment comment, @PathVariable(value="commentId") Long commentId) {
		return commentService.updateComment(comment, commentId);
	}
	
	@DeleteMapping("/{commentId}") 
	public ResponseEntity<?> deleteComment(@PathVariable(value="commentId") Long commentId) {
		return commentService.deleteComment(commentId);
	}
	
}
