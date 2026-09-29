package com.hazelngrazel.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazelngrazel.app.entity.GalleryEntity;

@Repository
public interface GalleryRepository extends JpaRepository<GalleryEntity, Long> {

	Page<GalleryEntity> findAllByIsDisabled(Boolean isDisabled, Pageable pageable);

}
