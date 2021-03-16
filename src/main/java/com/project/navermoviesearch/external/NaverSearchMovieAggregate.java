package com.project.navermoviesearch.external;

import com.project.navermoviesearch.code.GenreCode;
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
  private final GenreCode genreCode;

  public static NaverSearchMovieAggregate of(NaverSearchMoviesResponse response, GenreCode genreCode) {
    return NaverSearchMovieAggregate.builder()
        .movieList(response.getItems())
        .genreCode(genreCode)
        .build();
  }

  public boolean hasMovie() {
    return movieList.size() > 0;
  }
}
