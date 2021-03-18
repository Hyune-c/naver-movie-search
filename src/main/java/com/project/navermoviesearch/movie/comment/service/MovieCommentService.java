package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieCommentService {

  private final MovieCommentFindService movieCommentFindService;

  private final MovieService movieService;

  public Slice<MovieComment> findByMovieId(Pageable pageable, long movieId) {
    return movieCommentFindService.findByMovie(pageable, movieService.findByMovieId(movieId));
  }
}
