package com.project.navermoviesearch.movie.rating.service;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.rating.repository.MovieRatingRepository;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MovieRatingCreateService {

  private final MovieRatingRepository movieRatingRepository;

  @Transactional
  public MovieRating create(Movie movie, User user, int score) {
    return movieRatingRepository.save(MovieRating.of(movie, user, score));
  }
}
