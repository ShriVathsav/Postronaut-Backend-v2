package com.shriv.blog_app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.dao.TopicRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Topic;
import com.shriv.blog_app.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public List<Topic> getAllTopics() {
		return topicRepository.findAll();
	}

	@Override
	public Topic postTopic(Topic topic) {
		return topicRepository.save(topic);
	}

	@Override
	public ResponseEntity<?> deleteTopic(Long topicId) {
		return topicRepository.findById(topicId).map(topic -> {
            topicRepository.delete(topic);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("TopicId " + topicId +  " not found")
        );
	}

}
