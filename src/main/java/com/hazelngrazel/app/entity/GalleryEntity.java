package com.hazelngrazel.app.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;

@Entity
public class GalleryEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	 @Column(columnDefinition = "TEXT")
	private String imageUrl;
	
	@CreationTimestamp
	private LocalDateTime createdTm;
	
	private Boolean isDisabled = false;
	
	@UpdateTimestamp
	private LocalDateTime updatedTm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getCreatedTm() {
		return createdTm;
	}

	public void setCreatedTm(LocalDateTime createdTm) {
		this.createdTm = createdTm;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public LocalDateTime getUpdatedTm() {
		return updatedTm;
	}

	public void setUpdatedTm(LocalDateTime updatedTm) {
		this.updatedTm = updatedTm;
	}

}