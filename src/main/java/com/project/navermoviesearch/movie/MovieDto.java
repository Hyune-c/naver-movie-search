package com.project.navermoviesearch.movie;

import com.project.navermoviesearch.movie.entity.Movie;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDto {

  private final Movie movie;
  private final double averageRating;

  public static MovieDto of(Movie movie) {
    return MovieDto.builder()
        .movie(movie)
        .build();
  }

  public static MovieDto of(Movie movie, double averageRating) {
    return MovieDto.builder()
        .movie(movie)
        .averageRating(averageRating)
        .build();
  }

  public int commentCount() {
    return movie.getComments().size();
  }
}
