package com.project.navermoviesearch.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

  private final AddMovieService addMovieService;

  public void addMovie(String title) {
    addMovieService.addMovie(title);
  }
}
