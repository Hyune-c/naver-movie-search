package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieCommentDeleteService {

  private final MovieCommentRepository movieCommentRepository;

  public void delete(MovieComment movieComment) {
    movieComment.delete();
    movieCommentRepository.save(movieComment);
  }
}
