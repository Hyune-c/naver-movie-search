package com.project.navermoviesearch.moviesearch.dto;

import com.project.navermoviesearch.code.CountryCode;
import com.project.navermoviesearch.code.GenreCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMoviesRequestDto {

  private final String query; // 검색을 원하는 질의.

  private final Integer display; // 검색 결과 출력 건수를 지정한다. 최대 100까지 가능하다.

  private final Integer start; // 검색의 시작 위치를 지정할 수 있다. 최대 1000까지 가능하다.

  private final GenreCode genre; // 검색을 원하는 장르 코드를 의미한다.

  private final CountryCode country; // 검색을 원하는 국가 코드를 의미한다.

  private final Integer yearfrom; // 검색을 원하는 영화의 제작년도(최소)를 의미한다.

  private final Integer yearto; // 검색을 원하는 영화의 제작년도(최대)를 의미한다.

  public static SearchMoviesRequestDto forNaver(String query, GenreCode genre) {
    return SearchMoviesRequestDto.builder()
        .query(query)
        .display(100)
        .start(1)
        .genre(genre)
        .country(CountryCode.KR)
        .yearfrom(2020)
        .yearto(2020)
        .build();
  }
}
