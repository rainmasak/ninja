package com.indoorninja.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.indoorninja.assignment.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

	Photo findByFileName(String fileName);
	
	List<Photo> findAll();
	
}
