package com.shriv.blog_app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shriv.blog_app.model.ERole;
import com.shriv.blog_app.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole name);
}
