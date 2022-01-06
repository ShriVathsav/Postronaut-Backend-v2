package com.shriv.blog_app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name="image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="is_blob")
	private Boolean isBlob;
	
	@Column(name="image_index")
	private String imageIndex;
	
	@Column(name="image_url", nullable=false)
	private String imageUrl;

	@Column(name="created_at")
	private String createdAt;
	
	@Column(name="updated_at")
	private String updatedAt;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, 
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="image_id")
	private Blog blog;
	
}
