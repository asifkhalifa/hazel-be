package com.hazelngrazel.app.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.hazelngrazel.app.dto.GalleryDataRequest;
import com.hazelngrazel.app.dto.GalleryDataResponse;
import com.hazelngrazel.app.dto.PageResponse;
import com.hazelngrazel.app.entity.GalleryEntity;
import com.hazelngrazel.app.helper.FileUploadHelper;
import com.hazelngrazel.app.repository.GalleryRepository;
import com.hazelngrazel.app.serviceI.GalleryServiceI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class GalleryServiceImpl implements GalleryServiceI {
	
	@Autowired
	GalleryRepository galleryRepository;
	
	@Autowired
	FileUploadHelper fileUploadHelper;

	@Override
	public void uploadData(GalleryDataRequest request) {
		Map data = fileUploadHelper.uploadFile(request.getFile());
		String secureUrl = (String) data.get("secure_url");
		GalleryEntity entity = new GalleryEntity();
		entity.setIsDisabled(request.getIsDisabled());
		entity.setImageUrl(secureUrl);
		galleryRepository.save(entity);
	}
	
	@Override
	public PageResponse<GalleryDataResponse> getAllImages(Boolean isDisabled, Integer page, Integer size) {

	    Pageable pageable = PageRequest.of(page, size,
	            Sort.by(Sort.Direction.DESC, "createdTm"));

	    Page<GalleryEntity> galleryPage;

	    if (isDisabled == null) {
	        galleryPage = galleryRepository.findAll(pageable);
	    } else {
	        galleryPage = galleryRepository
	                .findAllByIsDisabled(isDisabled, pageable);
	    }

	    List<GalleryDataResponse> data = galleryPage.getContent()
	            .stream()
	            .map(entity -> {

	                GalleryDataResponse galleryResponse =
	                        new GalleryDataResponse();

	                galleryResponse.setId(entity.getId());
	                galleryResponse.setImageUrl(entity.getImageUrl());
	                galleryResponse.setIsDisabled(entity.getIsDisabled());
	                galleryResponse.setCreatedTm(entity.getCreatedTm());
	                galleryResponse.setUpdatedTm(entity.getUpdatedTm());

	                return galleryResponse;
	            })
	            .toList();

	    PageResponse<GalleryDataResponse> pageResponse =
	            new PageResponse<>();

	    pageResponse.setContent(data);
	    pageResponse.setPage(galleryPage.getNumber());
	    pageResponse.setSize(galleryPage.getSize());
	    pageResponse.setTotalElements(galleryPage.getTotalElements());
	    pageResponse.setTotalPages(galleryPage.getTotalPages());
	    pageResponse.setLast(galleryPage.isLast());

	    return pageResponse;
	}

	@Override
	public void updateStatus(Long id, Boolean isDisabled) {
		GalleryEntity entity = galleryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Image not found"));
		entity.setIsDisabled(isDisabled);
		galleryRepository.save(entity);
	}
}