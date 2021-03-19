package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieFindService {

  private final MovieRepository movieRepository;

  public Movie findById(long movieId) {
    return movieRepository.findByIdAndDeletedIsFalse(movieId)
        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
  }
}
