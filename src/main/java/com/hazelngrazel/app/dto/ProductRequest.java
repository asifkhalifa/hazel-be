package com.hazelngrazel.app.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

    private Long categoryId;

    private String productName;

    private String description;

    private MultipartFile file;
    
    private List<MultipartFile> files;
    
    private String imagesMetadata;
    
    private Boolean isDisabled;
    
    private Boolean isFeature;

    private List<ProductVariantRequest> variants;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getImagesMetadata() {
		return imagesMetadata;
	}

	public void setImagesMetadata(String imagesMetadata) {
		this.imagesMetadata = imagesMetadata;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public List<ProductVariantRequest> getVariants() {
		return variants;
	}

	public void setVariants(List<ProductVariantRequest> variants) {
		this.variants = variants;
	}

	public Boolean getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Boolean isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Boolean getIsFeature() {
		return isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}
}