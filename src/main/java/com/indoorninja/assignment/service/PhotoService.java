package com.indoorninja.assignment.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.indoorninja.assignment.model.GenericReponse;

/**
 * @author rainm
 * Service for saving and fetching images.
 */
public interface PhotoService {

	GenericReponse savePhoto(MultipartFile file);
	
	GenericReponse fetchPhoto(String fileName);
	
	GenericReponse deletePhoto(String fileName);
	
	List<String> getFiles();
	
}
