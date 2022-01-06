package com.shriv.blog_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.model.Topic;
import com.shriv.blog_app.service.TopicService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/topic")
public class TopicController {

	@Autowired
	private TopicService topicService;
	
	@PostMapping("/") 
	public Topic postTopic(@RequestBody Topic topic) {
		return topicService.postTopic(topic);
	}

	@GetMapping("/")
	public List<Topic> getAllTopics(){
		return topicService.getAllTopics();
	}
	
	@DeleteMapping("/{topicId}")
	public ResponseEntity<?> deleteTopic(@PathVariable Long topicId){
		return topicService.deleteTopic(topicId);
	}
}
