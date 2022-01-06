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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shriv.blog_app.dao.FollowRepository;
import com.shriv.blog_app.model.Follow;
import com.shriv.blog_app.service.FollowService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FollowService followService;
	
	@Autowired
	private FollowRepository followRepository;
	
	@GetMapping("/")
	public List<Follow> getAllFollowers(){
		return followRepository.findAll();
	}
	
	@GetMapping("/followers/{profileId}")
	public Page<Follow> getFollowers(@PathVariable Long profileId, Pageable pageable){
		return followService.getFollowers(profileId, pageable);
	}
	
	@GetMapping("/following/{profileId}")
	public Page<Follow> getFollowing(@PathVariable Long profileId, Pageable pageable){
		return followService.getFollowing(profileId, pageable);
	}
	
	@GetMapping("/findFollow/{fromId}/{toId}")
	public Boolean findFollowPerson(@PathVariable(value="fromId") Long fromId, @PathVariable(value="toId") Long toId){
		return followService.findFollowPerson(fromId, toId);
	}

	
	@PostMapping("/{fromId}/{toId}")
	public Follow followPerson(@PathVariable(name="fromId") Long fromId, @PathVariable(name="toId") Long toId) {
		return followService.followPerson(fromId, toId);
	}
	
	@DeleteMapping("/unfollow/{fromId}/{toId}") 
	public ResponseEntity<?> unfollowPerson(@PathVariable(name="fromId") Long fromId, @PathVariable(name="toId") Long toId) {
		return followService.unfollowPerson(fromId, toId);
	}

}
