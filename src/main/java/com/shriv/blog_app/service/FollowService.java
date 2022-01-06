package com.shriv.blog_app.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Follow;

public interface FollowService {

	public Follow followPerson(Long from, Long to);
	
	public ResponseEntity<?> unfollowPerson(Long from, Long to);
	
	public Page<Follow> getFollowers(Long profileId, Pageable pageable);
	
	public Page<Follow> getFollowing(Long profileId, Pageable pageable);
	
	public Boolean findFollowPerson(Long fromId, Long toId);
}
