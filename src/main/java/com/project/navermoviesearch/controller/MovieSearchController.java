package com.project.navermoviesearch.controller;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.controller.response.MovieListResponse;
import com.project.navermoviesearch.service.external.NaverSearchMovieService;
import com.project.navermoviesearch.service.external.dto.SearchMoviesRequestDto;
import com.project.navermoviesearch.service.external.dto.SearchMoviesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Naver 영화 검색")
@RequiredArgsConstructor
@RestController
public class MovieSearchController {

  private final NaverSearchMovieService naverSearchMovieService;

  @Operation(summary = "Naver 영화 검색")
  @GetMapping("/movies")
  public MovieListResponse getList(
      @Parameter(description = "검색 질의문") @RequestParam String query,
      @Parameter(description = "장르 코드") @RequestParam GenreCode genre) {
    SearchMoviesRequestDto request = SearchMoviesRequestDto.forNaver(query, genre);
    SearchMoviesResponseDto result = naverSearchMovieService.searchMovies(request);

    return MovieListResponse.of(result);
  }
}
