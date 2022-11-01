package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		
		Optional<Movie> obj = repository.findById(id);
		
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(Long genreId, Pageable pageable) {
		
		Genre genre = (genreId == 0 ) ? null : genreRepository.getOne(genreId);
		
		Page<Movie> page = repository.find(genre, pageable);
		
		return page.map(x -> new MovieCardDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findByMovie(Long movieId) {
	
		Movie movie = repository.getOne(movieId);
		List<Review> list = reviewRepository.findByMovie(movie);
		
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("Id not found " + movieId);
		}
		else {
			return list.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
		}			
	}
}
