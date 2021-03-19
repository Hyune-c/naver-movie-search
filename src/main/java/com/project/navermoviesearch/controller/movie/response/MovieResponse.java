package com.project.navermoviesearch.controller.movie.response;

import com.project.navermoviesearch.movie.MovieDto;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.entity.MovieGenre;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MovieResponse {

  @Schema(description = "영화 ID")
  private Long id;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "외부 링크")
  private String linkUrl;

  @Schema(description = "이미지")
  private String imageUrl;

  @Schema(description = "영문 제목")
  private String subTitle;

  @Schema(description = "개봉연도")
  private String publishYear;

  @Schema(description = "감독")
  private String directors;

  @Schema(description = "배우")
  private String actors;

  @Schema(description = "장르")
  private List<MovieGenre> genres;

  @Schema(description = "코멘트 수")
  private Integer commentCount;

  @Schema(description = "평균 점수")
  private Double averageRating;

  public static MovieResponse of(MovieDto dto) {
    Movie movie = dto.getMovie();
    return MovieResponse.builder()
        .id(movie.getId())
        .title(movie.getTitle())
        .linkUrl(movie.getLinkUrl())
        .imageUrl(movie.getImageUrl())
        .subTitle(movie.getSubTitle())
        .publishYear(movie.getPublishYear())
        .directors(movie.getDirectors())
        .actors(movie.getActors())
        .genres(movie.getGenres())
        .commentCount(dto.commentCount())
        .averageRating(dto.getAverageRating())
        .build();
  }
}
