package com.project.navermoviesearch.controller.movie.rating;

import com.project.navermoviesearch.config.annotation.LoginUser;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.service.MovieRatingService;
import com.project.navermoviesearch.movie.service.MovieService;
import com.project.navermoviesearch.user.entity.UserSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "영화 평점")
@Validated
@RequestMapping("/ratings")
@RequiredArgsConstructor
@RestController
public class MovieRatingController {

  private final MovieRatingService movieRatingService;
  private final MovieService movieService;

  @Operation(summary = "추가/수정")
  @ResponseStatus(HttpStatus.CREATED)
  @PutMapping("/movies/{movieId}")
  public Long createOrUpdate(
      @LoginUser UserSession session,
      @Parameter(description = "영화 ID") @PathVariable Long movieId,
      @Parameter(description = "평점") @RequestParam @Min(1) @Max(5) Integer score) {
    Movie movie = movieService.findByMovieId(movieId);
    return movieRatingService.createOrUpdate(movie, session.getUser(), score);
  }
}
