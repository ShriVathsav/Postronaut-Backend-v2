package com.shriv.blog_app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.shriv.blog_app.dto.BlogDto;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Topic;

public interface BlogService {
	
	public Blog createBlog(Blog blog, List<Long> topicList, MultipartFile coverPhoto, Long profileId);
	
	public Page<Blog> searchBlogsByTopic(List<Long> topicList, Pageable pageable);
	
	public Page<Blog> searchBlogsByContent(String searchContent, Pageable pageable);
	
	public Page<Blog> getBlogsByInterests(Long profileId, Pageable pageable);
	
	public Page<Blog> getBlogsByProfile(Long profileId, Pageable pageable, String blogStatus);
	
	public Blog getBlogById(Long blogId);
	
	public List<Blog> getBlogsByLikes(Long profileId, Pageable pageable);
	
	public List<Blog> getBlogsByComments(Long profileId, Pageable pageable);
	
	public Blog updateBlog(Long blogId, Blog blog, List<Long> topicList, MultipartFile coverPhoto, Long profileId);
	
	public ResponseEntity<?> deleteBlog(Long blogId, Long profileId);
}
