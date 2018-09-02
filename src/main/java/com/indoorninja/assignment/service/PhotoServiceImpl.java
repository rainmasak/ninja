package com.indoorninja.assignment.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.indoorninja.assignment.domain.Photo;
import com.indoorninja.assignment.model.GenericReponse;
import com.indoorninja.assignment.repository.PhotoRepository;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	PhotoRepository photoRepo;
	
	@Override
	public GenericReponse savePhoto(MultipartFile file) {
		
		GenericReponse response = new GenericReponse();
		
		String fileName = file.getOriginalFilename();
		
		// Check if filename already exists
		// Return error when file already exists in the database
		Photo photo = photoRepo.findByFileName(fileName);
		if (photo != null) {
			response.setStatus(HttpStatus.FORBIDDEN);
			response.setFileName(fileName);
			response.setMessage("Image with this filename already exists!");
			return response;
		}
		
		try {
			byte[] bytes = file.getBytes();
			// H2 database type, that accepts very large input data
		    Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
			
			// Save new image to the database
			photo = new Photo();
			photo.setFileName(fileName);
			photo.setImage(blob);
			
			photoRepo.save(photo);
			
			response.setStatus(HttpStatus.OK);
			response.setMessage("Image saved successfully!");
			response.setFileName(fileName);
		
		} catch (Exception e) {
			// proper logging here
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public GenericReponse fetchPhoto(String fileName) {
		
		GenericReponse response = new GenericReponse();
		
		Photo img = new Photo();
		img = photoRepo.findByFileName(fileName);
		if (img == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setMessage("Image with this filename was not found!");
			return response;
		}
		
		try {
			
			int blobLength = (int) img.getImage().length();
			byte[] image = img.getImage().getBytes(1l, blobLength);
			
			response.setFileName(fileName);
			response.setStatus(HttpStatus.OK);
			response.setImage(image);
		} catch (SQLException e) {
			// proper logging here
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public GenericReponse deletePhoto(String fileName) {
		Photo photo = photoRepo.findByFileName(fileName);
		GenericReponse response = new GenericReponse();
		if (photo == null) {
			response.setStatus(HttpStatus.NOT_FOUND);
			response.setFileName(fileName);
			response.setMessage(String.format("%s %s %s", "Image", fileName, "not found!"));
		} else {		
			photoRepo.delete(photo);
			response.setStatus(HttpStatus.OK);
			response.setFileName(fileName);
			response.setMessage(String.format("%s %s %s", "Image ", fileName, "successfully deleted!"));
		}
		return response;
	}
	
	@Override
	public List<String> getFiles() {
		List<String> res = new ArrayList<>();
		List<Photo> photos = photoRepo.findAll();
		for (Photo p : photos) {
			res.add(p.getFileName());
		}
		return res;
	}
	
}
