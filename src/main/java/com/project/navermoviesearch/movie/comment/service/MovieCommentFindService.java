package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MovieCommentFindService {

  private final MovieCommentRepository movieCommentRepository;

  @Transactional
  public Slice<MovieComment> findByMovie(Pageable pageable, Movie movie) {
    return movieCommentRepository.findAllByMovieAndDeletedIsFalse(pageable, movie);
  }

  @Transactional
  public Page<MovieComment> findByUser(Pageable pageable, User user) {
    return movieCommentRepository.findAllByUserAndDeletedIsFalse(pageable, user);
  }
}
