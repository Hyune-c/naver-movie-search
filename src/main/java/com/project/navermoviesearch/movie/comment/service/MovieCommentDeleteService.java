package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.comment.entity.MovieCommentDeleted;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentDeletedRepository;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieCommentDeleteService {

  private final MovieCommentRepository movieCommentRepository;
  private final MovieCommentDeletedRepository movieCommentDeletedRepository;

  @Transactional
  public void delete(MovieComment movieComment) {
    movieCommentDeletedRepository.save(MovieCommentDeleted.of(movieComment));
    movieCommentRepository.delete(movieComment);
  }
}
