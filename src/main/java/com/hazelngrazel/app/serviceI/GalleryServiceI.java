package com.hazelngrazel.app.serviceI;

import com.hazelngrazel.app.dto.GalleryDataRequest;
import com.hazelngrazel.app.dto.GalleryDataResponse;
import com.hazelngrazel.app.dto.PageResponse;

public interface GalleryServiceI {

	void uploadData(GalleryDataRequest request);

	PageResponse<GalleryDataResponse> getAllImages(Boolean isDisabled, Integer page, Integer size);

	void updateStatus(Long id, Boolean isDisabled);

}
