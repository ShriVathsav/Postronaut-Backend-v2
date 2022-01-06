package com.shriv.blog_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.dto.BlogDto;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Topic;
import com.shriv.blog_app.s3.AmazonClient;
import com.shriv.blog_app.service.BlogService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog")
public class BlogController { 
	
	@Autowired
	public BlogService blogService;
	
	@Autowired
	public BlogRepository blogRepository;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@Autowired
	public AmazonClient amazonClient;
	
	@PostMapping(value="/create/{profileId}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Blog postBlog(@RequestParam String blogString, @ModelAttribute MultipartFile coverPhoto, @PathVariable Long profileId) {
		System.out.println("Hello");
		System.out.println(coverPhoto);
		System.out.println("Hello2");

		BlogDto blogDto = null;
		try {
			blogDto = objectMapper.readValue(blogString, BlogDto.class);
			System.out.println(blogDto);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
		//System.out.println(blogDto);
		return blogService.createBlog(blogDto.getBlog(), blogDto.getTopicList(), coverPhoto, profileId);
	}
	
	@GetMapping("/")
	public List<Blog> getBlogs(){
		return blogRepository.findAll();
	}
	
	@GetMapping("/bytopics")
	public Page<Blog> searchBlogsByTopic(@RequestParam(value="topicList") List<Long> topicList, Pageable pageable){
		return blogService.searchBlogsByTopic(topicList, pageable);
	}
	
	@GetMapping("/bycontent")
	public Page<Blog> searchBlogsByContent(@RequestParam(value="searchContent") String searchContent, Pageable pageable){
		return blogService.searchBlogsByContent(searchContent, pageable);
	}
	
	@GetMapping("/byinterests/{profileId}")
	public Page<Blog> getBlogsByInterests(@PathVariable Long profileId, Pageable pageable){
		return blogService.getBlogsByInterests(profileId, pageable);
	}
	
	@GetMapping("/byprofile/{profileId}/{blogStatus}")
	public Page<Blog> getBlogsByProfile(@PathVariable Long profileId, Pageable pageable, @PathVariable String blogStatus){
		return blogService.getBlogsByProfile(profileId, pageable, blogStatus);
	}
	
	@GetMapping("/{blogId}")
	public Blog getBlogById(@PathVariable Long blogId) {
		return blogService.getBlogById(blogId);
	}
	
	@GetMapping("/bylike/{profileId}")
	public List<Blog> getBlogsByLikes(@PathVariable Long profileId, Pageable pageable){
		return blogService.getBlogsByLikes(profileId, pageable);
	}
	
	@GetMapping("/bycomment/{profileId}")
	public List<Blog> getBlogsByComments(@PathVariable Long profileId, Pageable pageable){
		return blogService.getBlogsByComments(profileId, pageable);
	}
	
	@PutMapping(value = "/{blogId}/update/{profileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Blog updateBlog(@PathVariable Long blogId, @PathVariable Long profileId, @RequestParam String blogString, @ModelAttribute MultipartFile coverPhoto) {
		System.out.println("Hello");
		System.out.println(coverPhoto);
		System.out.println("Hello2");

		BlogDto blogDto = null;
		try {
			blogDto = objectMapper.readValue(blogString, BlogDto.class);
			System.out.println(blogDto);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogService.updateBlog(blogId, blogDto.getBlog(), blogDto.getTopicList(), coverPhoto, profileId);
	}
	
	@DeleteMapping("/{blogId}/{profileId}")
	public ResponseEntity<?> deleteBlog(@PathVariable(value="blogId") Long blogId, @PathVariable(value="profileId") Long profileId) {
		return blogService.deleteBlog(blogId, profileId);
	}
}
