package com.devsuperior.movieflix.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {
	
	@Autowired
	private MovieService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		MovieDetailsDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findMoviesByGenre(
			@RequestParam(value = "genreId", defaultValue = "0") Long genreId, 
			Pageable pageable) {
		
		PageRequest pageRequest = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(), 
				Sort.by("title"));
		
		Page<MovieCardDTO> list = service.findAllPaged(genreId, pageRequest);
		
		return ResponseEntity.ok().body(list);
	}
		
	@GetMapping(value = "/{movieId}/reviews")
	public ResponseEntity<List<ReviewDTO>> findByMovie(@PathVariable Long movieId){
		List<ReviewDTO> list = service.findByMovie(movieId);
		return ResponseEntity.ok().body(list);
	}
	
}
