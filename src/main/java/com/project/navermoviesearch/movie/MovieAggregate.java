package com.project.navermoviesearch.movie;

import com.project.navermoviesearch.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieAggregate {

  private final Movie movie;

  public static MovieAggregate of(Movie movie) {
    return MovieAggregate.builder()
        .movie(movie)
        .build();
  }
}
