package com.hazelngrazel.app.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

@Entity
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String productName;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isDisabled = false;

    private Boolean isFeature = false;

    @CreationTimestamp
    private LocalDateTime createdTm;

    @UpdateTimestamp
    private LocalDateTime updatedTm;

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

	public Boolean getIsFeature() {
		return isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}
}