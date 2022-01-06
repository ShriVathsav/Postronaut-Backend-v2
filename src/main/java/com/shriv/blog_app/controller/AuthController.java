package com.shriv.blog_app.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.dao.RoleRepository;
import com.shriv.blog_app.dto.JwtResponse;
import com.shriv.blog_app.dto.LoginRequest;
import com.shriv.blog_app.dto.MessageResponse;
import com.shriv.blog_app.dto.SignupRequest;
import com.shriv.blog_app.model.ERole;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.model.Role;
import com.shriv.blog_app.security.JwtUtils;
import com.shriv.blog_app.service.impl.UserDetailsImpl;
import com.shriv.blog_app.service.impl.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getProfilePicUrl(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (profileRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Username is already taken!"));
		}

		if (profileRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Email is already in use!"));
		}

		// Create new user's account
		Profile profile = new Profile(signUpRequest.getEmail(), signUpRequest.getUsername(), 
							signUpRequest.getDescription(), signUpRequest.getCreatedAt(), signUpRequest.getUpdatedAt(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		profile.setRoles(roles);
		profileRepository.save(profile);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	@PostMapping("/role")
	public Role createRole() {
		Role role = new Role();
		role.setName(ERole.ROLE_USER);
		//role.setName(ERole.ROLE_ADMIN);
		return roleRepository.save(role);
	}
	
}