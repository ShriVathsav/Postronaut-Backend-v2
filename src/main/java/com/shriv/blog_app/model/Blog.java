package com.shriv.blog_app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name="blog")
public class Blog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="blog_title")
	private String blogTitle;
	
	@Column(name="blog_content", length=5000)
	private String blogContent;
	
	@Column(name="text_content", length=5000)
	private String textContent;
	
	@Column(name="cover_pic_url")
	private String coverPicUrl;
	
	@Column(name="blog_status", nullable=false)
	private String blogStatus;
	
	@Column(name="like_count")
	private Long likeCount = Long.valueOf(0L);
	
	@Column(name="comment_count")
	private Long commentCount = Long.valueOf(0L);
	
	@Column(name="images_list")
	private String imagesList;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated_at")
	private String updatedAt;

	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL)
	@JsonBackReference(value="bloglike")
	private List<Like> like;
	
	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL)
	@JsonBackReference(value="blogcomm")
	private List<Comment> comment;
	
	@OneToMany(mappedBy="blog", cascade=CascadeType.ALL)
	private List<Image> image;
	
	@ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="profile_id")
	private Profile profile;
	
	@ManyToMany(cascade= {CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name="blog_topic",
				joinColumns=@JoinColumn(name="blog_id"),
				inverseJoinColumns=@JoinColumn(name="topic_id"))	
	private List<Topic> topic;

	@Override
	public String toString() {
		return "Blog [id=" + id + ", blogTitle=" + blogTitle + ", blogContent=" + blogContent + ", coverPicUrl="
				+ coverPicUrl + ", blogStatus=" + blogStatus + ", likeCount=" + likeCount + ", commentCount="
				+ commentCount + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
