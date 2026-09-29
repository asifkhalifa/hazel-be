package com.hazelngrazel.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hazelngrazel.app.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

	Optional<CategoryEntity> findByCategoryNameIgnoreCase(String categoryName);

	List<CategoryEntity> findAllByIsDisabledFalse();

	Page<CategoryEntity> findByCategoryNameContainingIgnoreCaseAndIsDisabled(String name, Boolean isDisabled,
			Pageable pageable);

	Page<CategoryEntity> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);

	Page<CategoryEntity> findByIsDisabled(Boolean isDisabled, Pageable pageable);

}
