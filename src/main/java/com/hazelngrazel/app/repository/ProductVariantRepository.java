package com.hazelngrazel.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.ProductVariantEntity;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariantEntity, Long> {
    List<ProductVariantEntity> findByProductId(Long productId);
    void deleteByProductId(Long productId);
	List<ProductVariantEntity> findByProductIdIn(List<Long> productIds);
}
