package com.shriv.blog_app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Topic;

public interface BlogRepository extends JpaRepository<Blog, Long> {
	
	Optional<Blog> findByIdAndProfileId(Long id, Long profileId);
	
	Page<Blog> findByProfileIdAndBlogStatus(Long postId, Pageable pageable, String blogStatus);
	Page<Blog> findByProfileId(Long postId, Pageable pageable);
	
	
	//@Query(value="select * from blog left outer join blog_topic on blog.id=blog_topic.blog_id left outer join "+
	//"topic on blog_topic.topic_id=topic.id where topic.id in (9 , 11) group by blog.id", nativeQuery=true)
	 
	@Query("select b from Blog b left join b.topic topic where topic.id in :topicList and b.blogStatus='published' group by b.id")
	Page<Blog> findAllByTopics(@Param("topicList")List<Long> topicList, Pageable pageable);
	//List<Blog> findAllByTopics(@Param("username")String username);
	
	@Query("Select c from Blog c where c.textContent like %:searchContent% and c.blogStatus='published'")
	Page<Blog> findAllByContent(@Param("searchContent")String searchContent, Pageable pageable);
	
	List<Blog> findByTopic_TopicName(String userName);
	
	List<Blog> findByTopicIn(List<Topic> topicList);

}
