package com.shriv.blog_app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shriv.blog_app.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	@Modifying
	@Query("delete from Image u where u.id in ?1")
	public void deleteUsersWithIds(List<Long> imageIds);
	
}
