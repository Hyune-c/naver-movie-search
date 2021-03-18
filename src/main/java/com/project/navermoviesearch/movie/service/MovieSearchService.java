package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieSearchService {

  private final MovieRepository movieRepository;

  public List<Movie> search(String title) {
    return movieRepository.findAllByTitleContainingAndDeletedIsFalse(title);
  }

  public Slice<Movie> search(Pageable pageable, String title) {
    return movieRepository.findAllByTitleContainingAndDeletedIsFalse(pageable, title);
  }
}
