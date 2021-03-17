package com.project.navermoviesearch.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

  private final MovieAddService movieAddService;

  public void addMovie(String title) {
    movieAddService.addMovie(title);
  }
}
