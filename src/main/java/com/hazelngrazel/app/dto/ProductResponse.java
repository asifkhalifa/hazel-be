package com.hazelngrazel.app.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.hazelngrazel.app.entity.CategoryEntity;
import com.hazelngrazel.app.entity.ProductVariantEntity;
import com.hazelngrazel.app.entity.ProductImageEntity;

public class ProductResponse {
    private Long productId;
    private CategoryEntity category;
    private String productName;
    private String description;
    private String imageUrl;
    private Boolean isDisabled;
    private Boolean isFeature;
    private LocalDateTime createdTm;
    private LocalDateTime updatedTm;
    private List<ProductVariantEntity> variants;
    private List<ProductImageEntity> images;

    public List<ProductImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ProductImageEntity> images) {
        this.images = images;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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

    public List<ProductVariantEntity> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariantEntity> variants) {
        this.variants = variants;
    }

    public Boolean getIsFeature() {
        return isFeature;
    }

    public void setIsFeature(Boolean isFeature) {
        this.isFeature = isFeature;
    }
}
