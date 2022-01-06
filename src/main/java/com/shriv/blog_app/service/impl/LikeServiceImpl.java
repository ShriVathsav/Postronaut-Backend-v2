package com.shriv.blog_app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.dao.LikeRepository;
import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Like;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Override
	public Like postLike(Long blogId, Long profileId) {
		Optional<Profile> op = profileRepository.findById(profileId);
		Optional<Blog> op2 = blogRepository.findById(blogId);
		Optional<Like> op3 = likeRepository.findByBlogIdAndProfileId(blogId, profileId); 
		if(op.isPresent() && op2.isPresent() && !op3.isPresent()) {
			Profile profile = op.get();
			Blog blog = op2.get();
			Like like = new Like();
			profile.setLikedPosts(profile.getLikedPosts() + 1);
			blog.setLikeCount(blog.getLikeCount() + 1);
			like.setBlog(blog);
			like.setProfile(profile);
			return likeRepository.save(like);			
		}
		return null;
	}

	@Override
	public ResponseEntity<?> deleteLike(Long blogId, Long profileId) {
		Optional <Like> op = likeRepository.findByBlogIdAndProfileId(blogId, profileId);
		if(op.isPresent()){
			Like like = op.get();
			Profile profile = like.getProfile();
			Blog blog = like.getBlog();			
            likeRepository.delete(like);
            profile.setLikedPosts(profile.getLikedPosts() - 1);
			blog.setLikeCount(blog.getLikeCount() - 1);
			profileRepository.save(profile);
			blogRepository.save(blog);
            return ResponseEntity.ok().build();
        }else { 
        	throw new ResourceNotFoundException("Like not found with id " + blogId + " and postId " + profileId);
        }
	}

	@Override
	public Page<Like> getLikesByBlog(Long blogId, Pageable pageable) {
		return likeRepository.findByBlogId(blogId, pageable);
	}
	
	@Override
	public Page<Like> getLikesByProfile(Long profileId, Pageable pageable) {
		return likeRepository.findByProfileId(profileId, pageable);
	}

	@Override
	public Like getLikesByBlogAndProfile(Long blogId, Long profileId) {
		Optional<Like> op = likeRepository.findByBlogIdAndProfileId(blogId, profileId);
		
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
}
