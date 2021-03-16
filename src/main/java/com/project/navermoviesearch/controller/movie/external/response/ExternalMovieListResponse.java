package com.project.navermoviesearch.controller.movie.external.response;

import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesResponse.NaverMovie;
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
public class ExternalMovieListResponse {

  @Schema(description = "영화 목록")
  private List<ExternalMovieResponse> movies;

  public static ExternalMovieListResponse of(NaverSearchMovieAggregate aggregate) {
    List<ExternalMovieResponse> externalMovieResponseList = aggregate.getMovieList().stream()
        .map(ExternalMovieResponse::of)
        .collect(Collectors.toList());
    return ExternalMovieListResponse.builder()
        .movies(externalMovieResponseList)
        .build();
  }

  @Getter
  @Builder
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  @NoArgsConstructor
  public static class ExternalMovieResponse {

    @Schema(description = "영화 ID")
    private Long id;

    @Schema(description = "Naver 영화 코드")
    private String naverCode;

    @Schema(description = "영화 제목")
    private String title;

    public static ExternalMovieResponse of(NaverMovie movie) {
      ExternalMovieResponse.builder().build();
      return ExternalMovieResponse.builder()
          .naverCode(movie.getCode())
          .title(movie.getTitle())
          .build();
    }
  }
}
