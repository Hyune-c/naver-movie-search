package com.project.navermoviesearch.movie.comment.service;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.service.MovieService;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieCommentService {

  private final MovieCommentFindService movieCommentFindService;
  private final MovieCommentDeleteService movieCommentDeleteService;

  private final MovieService movieService;

  public Slice<MovieComment> findByMovieId(Pageable pageable, long movieId) {
    return movieCommentFindService.findByMovie(pageable, movieService.findByMovieId(movieId));
  }

  public Page<MovieComment> findByMe(Pageable pageable, User user) {
    return movieCommentFindService.findByUser(pageable, user);
  }

  public void delete(User user, long commentId) {
    MovieComment movieComment = movieCommentFindService.findById(commentId);

    if (validateDelete(user, movieComment)) {
      return;
    }

    movieCommentDeleteService.delete(movieComment);
  }

  /*
    검증: 작성자 여부,기 삭제 여부
   */
  private boolean validateDelete(User user, MovieComment movieComment) {
    if (!user.equals(movieComment.getUser())) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    return movieComment.getDeleted();
  }
}
