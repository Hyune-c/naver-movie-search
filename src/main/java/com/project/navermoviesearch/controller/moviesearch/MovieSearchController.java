package com.project.navermoviesearch.controller.moviesearch;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.controller.moviesearch.response.MovieListResponse;
import com.project.navermoviesearch.moviesearch.dto.SearchMoviesRequestDto;
import com.project.navermoviesearch.moviesearch.dto.SearchMoviesResponseDto;
import com.project.navermoviesearch.moviesearch.service.NaverSearchMovieService;
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
