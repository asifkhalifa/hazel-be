package com.hazelngrazel.app.dto;

import org.springframework.web.multipart.MultipartFile;

public class GalleryDataRequest {
	
	private MultipartFile file;
	private Boolean isDisabled;
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public Boolean getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
}