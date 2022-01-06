package com.shriv.blog_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.model.Profile;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	ProfileRepository profileRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Profile profile = profileRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(profile);
	}

}
