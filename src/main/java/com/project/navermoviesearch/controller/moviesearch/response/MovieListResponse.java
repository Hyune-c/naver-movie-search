package com.project.navermoviesearch.controller.moviesearch.response;

import com.project.navermoviesearch.moviesearch.dto.SearchMoviesResponseDto;
import com.project.navermoviesearch.moviesearch.dto.SearchMoviesResponseDto.SearchMovieDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MovieListResponse {

  @Schema(description = "영화 목록")
  private List<MovieResponse> movies;

  public static MovieListResponse of(SearchMoviesResponseDto dto) {
    List<MovieResponse> movieResponseList = dto.getItems().stream()
        .map(MovieResponse::of)
        .collect(Collectors.toList());
    return MovieListResponse.builder()
        .movies(movieResponseList)
        .build();
  }

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @NoArgsConstructor
  public static class MovieResponse {

    @Schema(description = "영화 ID")
    private Long id;

    @Schema(description = "Naver 영화 코드")
    private String naverCode;

    @Schema(description = "영화 제목")
    private String title;

    public static MovieResponse of(SearchMovieDto movie) {
      MovieResponse.builder().build();
      return MovieResponse.builder()
          .naverCode(movie.getCode())
          .title(movie.getTitle())
          .build();
    }
  }
}
