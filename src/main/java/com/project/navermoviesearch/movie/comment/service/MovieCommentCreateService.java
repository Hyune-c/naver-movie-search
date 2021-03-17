package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MovieCommentCreateService {

  private final MovieCommentRepository movieCommentRepository;

  @Transactional
  public MovieComment createComment(Movie movie, User user, String content) {
    return movieCommentRepository.save(MovieComment.of(movie, user, content));
  }
}
