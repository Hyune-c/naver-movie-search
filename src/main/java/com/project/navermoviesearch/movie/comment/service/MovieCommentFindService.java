package com.project.navermoviesearch.movie.comment.service;

import static com.project.navermoviesearch.config.handler.ErrorCode.COMMENTS_NOT_EXISTS;

import com.project.navermoviesearch.config.handler.exception.BusinessException;
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
    return movieCommentRepository.findAllByMovie(pageable, movie);
  }

  @Transactional
  public Page<MovieComment> findByUser(Pageable pageable, User user) {
    return movieCommentRepository.findAllByUser(pageable, user);
  }

  @Transactional
  public MovieComment findById(long commentId) {
    return movieCommentRepository.findById(commentId)
        .orElseThrow(() -> new BusinessException(COMMENTS_NOT_EXISTS));
  }
}
