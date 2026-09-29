package com.hazelngrazel.app.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

@Entity
public class CategoryEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(unique = true)
    private String categoryName;

    private Boolean isDisabled = false;

    @CreationTimestamp
    private LocalDateTime createdTm;

    @UpdateTimestamp
    private LocalDateTime updatedTm;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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