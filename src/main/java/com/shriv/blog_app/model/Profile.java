package com.shriv.blog_app.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="profile")
@Data
@NoArgsConstructor
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="email", nullable=false, unique=true)
	private String email;
	
	@Column(name="user_name", nullable=false)
	private String userName;

	@Column(name="password")
	private String password;
	
	@Column(name="profile_pic_url")
	private String profilePicUrl;
	
	@Column(name="description")
	private String description;
	
	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated_at")
	private String updatedAt;
	
	@Column(name="liked_posts")
	private Long likedPosts = Long.valueOf(0L);
	
	@Column(name="commented_posts")
	private Long commentedPosts = Long.valueOf(0L);
	
	@Column(name="follower_count")
	private Long followerCount = Long.valueOf(0L);
	
	@Column(name="following_count")
	private Long followingCount = Long.valueOf(0L);
	
	@Column(name="education")
	private String education;
	
	@Column(name="work")
	private String work;
	
	@Column(name="linkedin")
	private String linkedin;
	
	@Column(name="github")
	private String github;
	
	@Column(name="facebook")
	private String facebook;
	
	@Column(name="youtube")
	private String youtube;
	
	@Column(name="blogs_published")
	private Integer blogsPublished = Integer.valueOf(0);
	
	@Column(name="blogs_saved")
	private Integer blogsSaved = Integer.valueOf(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy="profile", cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JsonBackReference(value="blogprof")
	private List<Blog> blog;
	
	@OneToMany(mappedBy="profile", cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JsonBackReference(value="likeprof")
	private List<Like> like;
	
	@OneToMany(mappedBy="profile", cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JsonBackReference(value="commprof")
	private List<Comment> comment;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(name="profile_topic",
				joinColumns=@JoinColumn(name="profile_id"),
				inverseJoinColumns=@JoinColumn(name="topic_id"))
	private List<Topic> topic;
	
	@OneToMany
	@JoinColumn(name="commented_blog_id", referencedColumnName="id")
	private List<Blog> commentedBlog;
	
	@OneToMany(mappedBy="to", cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JsonBackReference(value="followerprof")
    private List<Follow> followers;

    @OneToMany(mappedBy="from", cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference(value="followingprof")
    private List<Follow> following;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "relation",
                joinColumns = @JoinColumn(name = "profile_id"),
                inverseJoinColumns = @JoinColumn(name = "following2_id"))
    private List<Profile> following2;
    
    @ManyToMany(mappedBy = "following2")
    private List<Profile> followers2;

	public Profile(String email, String userName, String description, String createdAt, String updatedAt, String password) {
		this.email = email;
		this.userName = userName;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.password = password;
	}

    
}
