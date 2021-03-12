package com.project.navermoviesearch.service.external.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMoviesResponseDto {

  private final String lastBuildDate;

  private final Integer total;

  private final Integer start;

  private final Integer display;

  private final List<SearchMovieDto> items;

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class SearchMovieDto {

    private final String title;
    private final String link;
    private final String image;
    private final String subtitle;
    private final String pubDate;
    private final String director;
    private final String actor;
    private final String userRating;

    public String getTitle() {
      return this.title.replaceAll("<[^>]*>", "");
    }

    public String getCode() {
      return link.split("code=")[1];
    }
  }
}
