package com.shriv.blog_app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.dao.TopicRepository;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.model.Topic;
import com.shriv.blog_app.s3.AmazonClient;
import com.shriv.blog_app.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private AmazonClient amazonClient;

	@Override
	public Profile getProfileById(Long profileId) {
		Optional<Profile> op = profileRepository.findById(profileId);
		
		if(op.isPresent()) {
			return op.get();
		}
		
		return null;
	}

	@Override
	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public Profile editProfile(Long profileId, Profile profile) {
		if(!profileRepository.existsById(profileId)) {
			throw new ResourceNotFoundException("ProfileId " + profileId + " not found");
		}

        return profileRepository.findById(profileId).map(prof -> {
            prof.setUserName(profile.getUserName());            
            prof.setDescription(profile.getDescription());
            prof.setEducation(profile.getEducation());
            prof.setWork(profile.getWork());
            prof.setLinkedin(profile.getLinkedin());
            prof.setGithub(profile.getGithub());
            prof.setYoutube(profile.getYoutube());
            prof.setFacebook(profile.getFacebook());
            prof.setUpdatedAt(profile.getUpdatedAt());
            prof.setProfilePicUrl(profile.getProfilePicUrl());
            return profileRepository.save(prof);
        }).orElseThrow(() -> new ResourceNotFoundException("ProfileId " + profileId + "not found"));
	}

	@Override
	public Profile addTopicsToProfile(List<Long> topic, Long profileId) {
		List<Topic> top = topicRepository.findAllById(topic);
		return profileRepository.findById(profileId).map(prof -> {
            prof.setTopic(top);
            return profileRepository.save(prof);
        }).orElseThrow(() -> new ResourceNotFoundException("ProfileId " + profileId + "not found"));
	}

	@Override
	public String uploadProfileImage(String imageUrl, Long profileId) {
		Profile profile = getProfileById(profileId);
		String existingImageUrl = profile.getProfilePicUrl();
		if(existingImageUrl != null) {
			amazonClient.deleteFileFromS3Bucket(existingImageUrl);
		}
		profile.setProfilePicUrl(imageUrl);
		profileRepository.save(profile);
		return imageUrl;
	}

	@Override
	public void deleteProfileImage(String imageUrl, Long profileId) {
		Profile profile = getProfileById(profileId);
		profile.setProfilePicUrl(null);
		profileRepository.save(profile);
	}


}
