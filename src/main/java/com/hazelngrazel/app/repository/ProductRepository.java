package com.hazelngrazel.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
    Page<ProductEntity> findByProductNameContainingIgnoreCaseAndIsDisabled(String name, Boolean isDisabled, Pageable pageable);
    Page<ProductEntity> findByProductNameContainingIgnoreCase(String name, Pageable pageable);
    Page<ProductEntity> findByIsDisabled(Boolean isDisabled, Pageable pageable);
    boolean existsByCategoryCategoryId(Long categoryId);
    List<ProductEntity> findTop5ByIsFeatureTrueAndIsDisabledFalseOrderByUpdatedTmDesc();
}
