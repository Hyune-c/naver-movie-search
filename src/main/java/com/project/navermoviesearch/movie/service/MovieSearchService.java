package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieSearchService {

  private final MovieRepository movieRepository;

  public List<Movie> search(String title) {
    return movieRepository.findAllByTitleContainingAndDeletedIsFalse(title);
  }
}
