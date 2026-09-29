package com.hazelngrazel.app.helper;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class FileUploadHelper {
	
	@Autowired
	Cloudinary cloudinary;
	
	public Map uploadFile(MultipartFile file) {

		if (file.isEmpty()) {
		    throw new RuntimeException("Please select a file");
		}
		
		if (file.getSize() > 5 * 1024 * 1024) {
		    throw new RuntimeException("File size must not exceed 5 MB");
		}
			
		try {
			Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());
			return data;
		} catch (IOException e) {
			throw new RuntimeException("Image uploading fail");
		}		
	}

}
