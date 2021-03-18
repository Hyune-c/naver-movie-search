package com.project.navermoviesearch.movie.rating.service;

import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.rating.repository.MovieRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MovieRatingUpdateService {

  private final MovieRatingRepository movieRatingRepository;

  @Transactional
  public MovieRating update(MovieRating movieRating, int score) {
    movieRating.update(score);
    return movieRatingRepository.save(movieRating);
  }
}
