package com.shriv.blog_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.FollowRepository;
import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Follow;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowRepository followRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Override
	public Follow followPerson(Long from, Long to) {
		Optional<Profile> op = profileRepository.findById(from);
		Optional<Profile> op2 = profileRepository.findById(to);
		Optional<Follow> op3 = followRepository.findByFromIdAndToId(from, to);
		if(!op3.isPresent()) {
			if(op.isPresent() && op2.isPresent()) {
				Follow follow = new Follow();
				Profile fromProfile = op.get();
				Profile toProfile = op2.get();
				fromProfile.setFollowingCount(fromProfile.getFollowingCount() + 1);
				toProfile.setFollowerCount(toProfile.getFollowerCount() + 1);
				follow.setFrom(fromProfile);
				follow.setTo(toProfile);
				return followRepository.save(follow);			
			} else {
				throw new ResourceNotFoundException("ProfileId " + from + " or ProfileId " + to +  " not found");
			}
		}
		else {
			return null;
		}
	}

	@Override
	public ResponseEntity<?> unfollowPerson(Long from, Long to) {
		return followRepository.findByFromIdAndToId(from, to).map(follow -> {
			Profile fromProfile = follow.getFrom();
			Profile toProfile = follow.getTo();
            followRepository.delete(follow);
            fromProfile.setFollowingCount(fromProfile.getFollowingCount() - 1);
			toProfile.setFollowerCount(toProfile.getFollowerCount() - 1);
			profileRepository.save(fromProfile);
			profileRepository.save(toProfile);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("ProfileId " + from + " or ProfileId " + to +  " not found")
        );
	}

	@Override
	public Page<Follow> getFollowers(Long profileId, Pageable pageable) {
		return followRepository.findByToId(profileId, pageable);
	}

	@Override
	public Page<Follow> getFollowing(Long profileId, Pageable pageable) {
		return followRepository.findByFromId(profileId, pageable);
	}
	
	@Override
	public Boolean findFollowPerson(Long fromId, Long toId) {
		Optional<Follow> op = followRepository.findByFromIdAndToId(fromId, toId);
		if(op.isPresent()) {
			return true;	
		}
		return false;
	}

}
