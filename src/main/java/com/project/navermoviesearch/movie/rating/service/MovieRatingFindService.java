package com.project.navermoviesearch.movie.rating.service;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.rating.repository.MovieRatingRepository;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieRatingFindService {

  private final MovieRatingRepository movieRatingRepository;

  public MovieRating findByMovieAndUser(Movie movie, User user) {
    return movieRatingRepository.findByMovieAndUser(movie, user)
        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
  }
}
