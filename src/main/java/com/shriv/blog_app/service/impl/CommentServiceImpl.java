package com.shriv.blog_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.dao.CommentRepository;
import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Comment;
import com.shriv.blog_app.model.Like;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Comment postComment(Comment comment, Long profileId, Long blogId) {	
		Optional<Profile> op = profileRepository.findById(profileId);
		Optional<Blog> op2 = blogRepository.findById(blogId);
		if(op.isPresent() && op2.isPresent()) {
			Profile prof = op.get();
			Blog blog = op2.get();
			prof.setCommentedPosts(prof.getCommentedPosts() + 1);
			blog.setCommentCount(blog.getCommentCount() + 1);
			comment.setProfile(prof);
			comment.setBlog(blog);
			return commentRepository.save(comment);			
		}
		return null;
	}
	
	Comment postSampleComment(Comment comment, Long profileId, Long blogId) {
		Optional<Profile> op = profileRepository.findById(profileId);
		Optional<Blog> op2 = blogRepository.findById(blogId);
		if(op.isPresent() && op2.isPresent()) {
			Profile prof = op.get();
			Blog blog = op2.get();
			comment.setProfile(prof);
			comment.setBlog(blog);
			return commentRepository.save(comment);			
		}
		return null;
	}

	@Override
	public Comment updateComment(Comment comment, Long commentId) {
		if(!commentRepository.existsById(commentId)) {
			throw new ResourceNotFoundException("PostId " + commentId + " not found");
		}

        return commentRepository.findById(commentId).map(comm -> {
            comm.setContent(comment.getContent());
            return commentRepository.save(comm);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + commentId + "not found"));
	}

	@Override
	public ResponseEntity<?> deleteComment(Long commentId) {
		return commentRepository.findById(commentId).map(comment -> {
			Profile profile = comment.getProfile();
			Blog blog = comment.getBlog();
			profile.setCommentedPosts(profile.getCommentedPosts() - 1);
			blog.setCommentCount(blog.getCommentCount() - 1);
            commentRepository.delete(comment);
            profileRepository.save(profile);
            blogRepository.save(blog);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("Comment not found with id " + commentId)
        );
	}

	@Override
	public List<Comment> getAllComments() {
		return commentRepository.findAll();
	}

	@Override
	public Page<Comment> getCommentsByBlog(Long blogId, Pageable pageable) {
		return commentRepository.findByBlogId(blogId, pageable);
	}
	
	@Override
	public Page<Comment> getCommentsByProfile(Long profileId, Pageable pageable) {
		return commentRepository.findByProfileId(profileId, pageable);
	}

}
