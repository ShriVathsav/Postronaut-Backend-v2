package com.shriv.blog_app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shriv.blog_app.dao.BlogRepository;
import com.shriv.blog_app.dao.CommentRepository;
import com.shriv.blog_app.dao.LikeRepository;
import com.shriv.blog_app.dao.ProfileRepository;
import com.shriv.blog_app.dao.TopicRepository;
import com.shriv.blog_app.dto.BlogContentDto;
import com.shriv.blog_app.dto.BlogDto;
import com.shriv.blog_app.exception.ResourceNotFoundException;
import com.shriv.blog_app.model.Blog;
import com.shriv.blog_app.model.Image;
import com.shriv.blog_app.model.Profile;
import com.shriv.blog_app.model.Topic;
import com.shriv.blog_app.s3.AmazonClient;
import com.shriv.blog_app.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private LikeRepository likeRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	

	@Override
	public Blog createBlog(Blog blog, List<Long> topicList, MultipartFile coverPhoto, Long profileId) {
		Optional<Profile> op = profileRepository.findById(profileId);
		if(op.isPresent()) {
			Profile prof = op.get();
			//Blog blog = new Blog();
			//blog.setBlogContent(composeBlogContent(blogDto));
			
			List<Topic> topics = topicRepository.findAllById(topicList);
			
			String coverPhotoUrl = null;
			if(coverPhoto != null) {
				coverPhotoUrl = amazonClient.uploadFile(coverPhoto);
			}
			
			blog.setCoverPicUrl(coverPhotoUrl);
			blog.setProfile(prof);
			blog.setTopic(topics);
			System.out.println(blog.getBlogStatus());
			System.out.println(prof.getBlogsPublished());
			System.out.println(prof.getBlogsSaved());
			if(blog.getBlogStatus().equals("published")) {
				prof.setBlogsPublished(prof.getBlogsPublished() + 1);
			} else if(blog.getBlogStatus().equals("saved")) {
				prof.setBlogsSaved(prof.getBlogsSaved() + 1);
			}
			profileRepository.save(prof);
			return blogRepository.save(blog);
		}
		return null;
	}
	
	@Override
	public Page<Blog> searchBlogsByTopic(List<Long> topicList, Pageable pageable) {        
		
		//blogRepository.findByTopic_TopicName("Ninth Topic");
        
		//blogRepository.findByTopic(topic);
		return blogRepository.findAllByTopics(topicList, pageable);
		
		//return blogRepository.findByTopicIn(topicLists);
	}
	
	@Override
	public Page<Blog> searchBlogsByContent(String searchContent, Pageable pageable) {        
		
		//blogRepository.findByTopic_TopicName("Ninth Topic");
        
		//blogRepository.findByTopic(topic);
		return blogRepository.findAllByContent(searchContent, pageable);
		
		//return blogRepository.findByTopicIn(topicLists);
	}

	@Override
	public Page<Blog> getBlogsByInterests(Long profileId, Pageable pageable) {
        
        //topicRepository.findAllById(topicList).stream()
        	//	.forEach((topic) -> blogList.addAll(topic.getBlog()));
		
		List<Long> topicList = new ArrayList<Long>();
		
		Optional<Profile> op = profileRepository.findById(profileId);
		
		if(op.isPresent()) {
			List<Topic> interestedTopics = op.get().getTopic();				
			for(Topic topic : interestedTopics) {
				topicList.add(topic.getId());
			}
		}
		
		//blogRepository.findByTopic_TopicName("Ninth Topic");
        
		//blogRepository.findByTopic(topic);
		return blogRepository.findAllByTopics(topicList, pageable);
		
		//return blogRepository.findByTopicIn(topicLists);
	}

	@Override
	public Page<Blog> getBlogsByProfile(Long profileId, Pageable pageable, String blogStatus) {
		return blogRepository.findByProfileIdAndBlogStatus(profileId, pageable, blogStatus);
	}

	@Override
	public Blog getBlogById(Long blogId) {
		return blogRepository.findById(blogId).map(blog -> {
            return blog;
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("Blog with id " + blogId + " not found")
        );	
	}

	@Override
	public List<Blog> getBlogsByLikes(Long profileId, Pageable pageable) {
		List<Blog> blogList = new ArrayList<Blog>();
        likeRepository.findByProfileId(profileId, pageable).getContent().stream()
        	.forEach((like) -> blogList.add(like.getBlog()));
        
		return blogList;
	}

	@Override
	public List<Blog> getBlogsByComments(Long profileId, Pageable pageable) {
		List<Blog> blogList = new ArrayList<Blog>();
        commentRepository.findByProfileId(profileId, pageable).getContent().stream()
        	.forEach((comment) -> blogList.add(comment.getBlog()));
        
		return blogList;
	}

	@Override
	public Blog updateBlog(Long blogId, Blog blog, List<Long> topicList, MultipartFile coverPhoto, Long profileId) {
		
		if(!blogRepository.existsById(blogId)) {
			throw new ResourceNotFoundException("Blog with id " + blogId + " not found");
		}

        return blogRepository.findById(blogId).map(post -> {     
        	
        	List<Topic> topics = topicRepository.findAllById(topicList);
        	Optional<Profile> op = profileRepository.findById(profileId);
        	if(op.isPresent()) {
				Profile prof = op.get();			
				if(post.getBlogStatus().equals("saved")) {
					if(blog.getBlogStatus().equals("published")) {
						prof.setBlogsPublished(prof.getBlogsPublished() + 1);
						prof.setBlogsSaved(prof.getBlogsSaved() - 1);
					}
				}
				profileRepository.save(prof);
			}
        	post.setBlogTitle(blog.getBlogTitle());
        	post.setBlogStatus(blog.getBlogStatus());
        	
        	String existingCoverPicUrl = post.getCoverPicUrl();
        	String newCoverPicUrl = blog.getCoverPicUrl();
        	post.setCoverPicUrl(blog.getCoverPicUrl());
        	post.setBlogContent(blog.getBlogContent());
        	post.setTextContent(blog.getTextContent());
        	post.setImagesList(blog.getImagesList());
        	post.setTopic(topics);
        	
        	String coverPhotoUrl = null;
        	//System.out.println(existingCoverPicUrl.equals(""));
        	System.out.println(newCoverPicUrl);
    		if(coverPhoto != null) {
    			if(existingCoverPicUrl != null && !existingCoverPicUrl.equals("")) {
    				System.out.println("COVER PIC NOT NULL ECPU NOT NULL");
    				amazonClient.deleteFileFromS3Bucket(existingCoverPicUrl);
    				System.out.println("DELETED COVER PICTURE");
    			}
    			System.out.println("COVER PIC NOT NULL ECPU NULL");
	    		coverPhotoUrl = amazonClient.uploadFile(coverPhoto);
				post.setCoverPicUrl(coverPhotoUrl);
    		} else if(coverPhoto == null && newCoverPicUrl == null && (existingCoverPicUrl != null && !existingCoverPicUrl.equals(""))) {
    			System.out.println("COVER PIC NULL NCPU NULL");
    			amazonClient.deleteFileFromS3Bucket(existingCoverPicUrl);
				System.out.println("DELETED COVER PICTURE");
    		}
    		
            return blogRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + blogId + "not found"));
	}

	@Override
	public ResponseEntity<?> deleteBlog(Long blogId, Long profileId) {
		Optional<Profile> op = profileRepository.findById(profileId);
		return blogRepository.findByIdAndProfileId(blogId, profileId).map(blog -> {
			List<Image> imageList = blog.getImage();
			if(op.isPresent()) {
				Profile prof = op.get();			
				if(blog.getBlogStatus().equals("published")) {
					prof.setBlogsPublished(prof.getBlogsPublished() - 1);
				} else {
					prof.setBlogsSaved(prof.getBlogsSaved() - 1);
				}
				profileRepository.save(prof);
			}
			for(Image image : imageList) {
				amazonClient.deleteFileFromS3Bucket(image.getImageUrl());
			}
            blogRepository.delete(blog);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> 
        	new ResourceNotFoundException("Blog with id " + blogId + " not found")
        );		
	}

	
	public String uploadImage(String image) {
		return image;
	}
}
