package com.shriv.blog_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Topic;

public interface TopicService {
	
	public Topic postTopic(Topic topic);

	public List<Topic> getAllTopics();
	
	public ResponseEntity<?> deleteTopic(Long topicId);
	
}
