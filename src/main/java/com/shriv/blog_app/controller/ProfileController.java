package com.shriv.blog_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.model.Follow;
import com.shriv.blog_app.model.Image;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.s3.AmazonClient;
import com.shriv.blog_app.service.ProfileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@GetMapping("/getAll")
	public List<Profile> getAllProfiles() {
		return profileRepository.findAll();
	}
	
	@GetMapping("/{profileId}")
	public Profile getProfileById(@PathVariable Long profileId) {
		return profileService.getProfileById(profileId);
	}
	
	@PutMapping("/{profileId}")
	public Profile editProfile(@PathVariable Long profileId, @RequestBody Profile profile) {
		return profileService.editProfile(profileId, profile);
	}
	
	@PostMapping("/create")
	public Profile createProfile(@RequestBody Profile profile) {		
		//Profile profiles = new Profile("email", "firstname", "lastname");
		//System.out.println(profile);
		return profileService.createProfile(profile);		
	}
	
	@PostMapping(value= "/uploadprofileimage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadProfileImage(@RequestParam MultipartFile file, @RequestParam Long profileId) {
		String fileUrl = amazonClient.uploadFile(file);
		profileService.uploadProfileImage(fileUrl, profileId);
		return fileUrl;
	}
	
	@DeleteMapping("/deleteprofileimage")
	public String deleteProfileImage(@RequestParam(name="imageUrl") String imageUrl, @RequestParam(name="profileId") Long profileId) {
		String message = amazonClient.deleteFileFromS3Bucket(imageUrl);
		profileService.deleteProfileImage(imageUrl, profileId);
		return message;
	}
	
	@PostMapping("/addtopic/{profileId}")
	public Profile addTopicsToProfile(@RequestBody List<Long> topicList, @PathVariable Long  profileId) {
		return profileService.addTopicsToProfile(topicList, profileId);
	}
	
	@PostMapping("/follow/{fromId}/{toId}")
	public Profile followPerson(@PathVariable(name="fromId") Long fromId, @PathVariable(name="toId") Long toId) {
		Profile prof1 = profileRepository.findById(fromId).get();
		Profile prof2 = profileRepository.findById(toId).get();
		prof1.getFollowers2().add(prof2);
		return profileRepository.save(prof1);
	}
}


