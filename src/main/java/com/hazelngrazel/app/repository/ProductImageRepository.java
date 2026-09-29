package com.hazelngrazel.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.ProductImageEntity;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
    List<ProductImageEntity> findByProductIdOrderByPositionAsc(Long productId);
    void deleteByProductId(Long productId);
	List<ProductImageEntity> findByProductIdInOrderByPositionAsc(List<Long> productIds);
}
