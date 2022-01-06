package com.shriv.blog_app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shriv.blog_app.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	
	Optional<Profile> findByEmail(String email);

	Boolean existsByEmail(String email);
	
	Boolean existsByUserName(String userName);
	
}
