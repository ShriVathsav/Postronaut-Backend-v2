package com.shriv.blog_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.shriv.blog_app.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFromIdAndToId(Long fromId, Long toId);
	
	Page<Follow> findByFromId(Long fromId, Pageable pageable);
	
	Page<Follow> findByToId(Long toId, Pageable pageable);
}
