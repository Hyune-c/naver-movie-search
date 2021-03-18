package com.project.navermoviesearch.external;

import static com.project.navermoviesearch.config.handler.ErrorCode.EXTERNAL_NOT_EXIST_MOVIE;

import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesResponse;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesResponse.NaverMovie;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverSearchMovieAggregate {

  private final List<NaverMovie> movieList;

  public static NaverSearchMovieAggregate of(NaverSearchMoviesResponse response) {
    return NaverSearchMovieAggregate.builder()
        .movieList(response.getItems())
        .build();
  }

  public boolean existsMovieByTitle(String title) {
    return movieList.stream()
        .filter(movie -> title.equals(movie.getTitle()))
        .count() == 1;
  }

  public NaverMovie findByTitle(String title) {
    return movieList.stream()
        .filter(movie -> title.equals(movie.getTitle()))
        .findFirst()
        .orElseThrow(() -> new BusinessException(EXTERNAL_NOT_EXIST_MOVIE));
  }
}
