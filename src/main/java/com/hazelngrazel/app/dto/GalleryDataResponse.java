package com.hazelngrazel.app.dto;

import java.time.LocalDateTime;

public class GalleryDataResponse {
	private Long id;
	private String imageUrl;
	private Boolean isDisabled;
	private LocalDateTime createdTm;
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
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
	public LocalDateTime getCreatedTm() {
		return createdTm;
	}
	public void setCreatedTm(LocalDateTime createdTm) {
		this.createdTm = createdTm;
	}
	public LocalDateTime getUpdatedTm() {
		return updatedTm;
	}
	public void setUpdatedTm(LocalDateTime updatedTm) {
		this.updatedTm = updatedTm;
	}
}
