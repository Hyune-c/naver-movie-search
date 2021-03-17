package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.movie.MovieAggregate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

  private final MovieAddService movieAddService;
  private final MovieSearchService movieSearchService;

  public void addMovie(String title) {
    movieAddService.addMovie(title);
  }

  public List<MovieAggregate> search(String title) {
    return movieSearchService.search(title).stream()
        .map(MovieAggregate::of)
        .collect(Collectors.toList());
  }
}
