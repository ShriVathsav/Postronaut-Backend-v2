package com.shriv.blog_app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Table(name="topic")
@Data
public class Topic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="topic_name", nullable=false, unique=true)
	private String topicName;
	
	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH}, mappedBy="topic")
	@JsonBackReference(value="profiletopic")
	private List<Profile> profile = new ArrayList<>();
	
	@ManyToMany(cascade={CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH}, mappedBy="topic")

	@JsonBackReference(value="blogtopic")
	private List<Blog> blog = new ArrayList<>();

}
