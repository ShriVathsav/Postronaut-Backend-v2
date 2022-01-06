package com.shriv.blog_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.shriv.blog_app.model.Profile;

public interface ProfileService {

	public Profile getProfileById(Long profileId);
	
	public Profile createProfile(Profile profile);
	
	public Profile editProfile(Long profileId, Profile profile);
	
	public Profile addTopicsToProfile(List<Long> topic, Long profileId);
	
	public String uploadProfileImage(String imageUrl, Long profileId);
	
	public void deleteProfileImage(String imageUrl, Long profileId);
	
}

