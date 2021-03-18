package com.project.navermoviesearch.movie.rating.service;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.service.MovieService;
import com.project.navermoviesearch.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieRatingService {

  private final MovieRatingFindService movieRatingFindService;
  private final MovieRatingCreateService movieRatingCreateService;
  private final MovieRatingUpdateService movieRatingUpdateService;

  private final MovieService movieService;

  /*
    자신이 작성한 평점이 존재한다면 수정합니다.
   */
  public long createOrUpdate(long movieId, User user, int score) {
    Movie movie = movieService.findByMovieId(movieId);
    MovieRating movieRating;

    // TODO: 커스텀 익셉션 세부 처리 후 개선합니다.
    try {
      movieRating = movieRatingFindService.findByMovieAndUser(movie, user);
    } catch (BusinessException ex) {
      if (ex.getErrorCode().equals(ErrorCode.NOT_FOUND)) {
        return movieRatingCreateService.create(movieService.findByMovieId(movieId), user, score).getId();
      }

      throw new BusinessException(ErrorCode.UNKNOWN);
    }

    return movieRatingUpdateService.update(movieRating, score).getId();
  }
}
