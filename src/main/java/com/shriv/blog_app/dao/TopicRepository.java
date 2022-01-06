package com.shriv.blog_app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.shriv.blog_app.model.Like;
import com.shriv.blog_app.model.Topic;


public interface TopicRepository extends JpaRepository<Topic, Long> {

}
