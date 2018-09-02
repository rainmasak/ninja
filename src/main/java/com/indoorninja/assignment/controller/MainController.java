package com.indoorninja.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.indoorninja.assignment.model.GenericReponse;
import com.indoorninja.assignment.service.PhotoService;

@Controller
public class MainController {
	
	@Autowired
	PhotoService photoService;
	
	@RequestMapping(value = "/photo", method = RequestMethod.POST)
	public @ResponseBody GenericReponse upload(@RequestBody MultipartFile file) {
		return photoService.savePhoto(file);
	}
	
	@RequestMapping(value = "/photo", method = RequestMethod.GET)
	public @ResponseBody GenericReponse view(@RequestParam("fileName") String fileName) {
		return photoService.fetchPhoto(fileName);
	}
	
	@RequestMapping(value = "/photo", method = RequestMethod.DELETE)
	public @ResponseBody GenericReponse delete(@RequestBody String fileName) {
		return photoService.deletePhoto(fileName);
	}
	
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public @ResponseBody List<String> files() {
		return photoService.getFiles();
	}
	
}
