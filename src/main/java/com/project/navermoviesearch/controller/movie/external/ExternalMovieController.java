package com.project.navermoviesearch.controller.movie.external;

import com.project.navermoviesearch.controller.movie.external.response.ExternalMovieListResponse;
import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.service.NaverSearchMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "영화 (외부 서비스)")
@RequiredArgsConstructor
@RestController
public class ExternalMovieController {

  private final NaverSearchMovieService naverSearchMovieService;

  @Operation(summary = "Naver 영화 검색", description = "외부 서비스에서 영화 정보를 검색합니다.")
  @GetMapping("/external/movies")
  public ExternalMovieListResponse searchList(
      @Parameter(description = "검색 질의문") @RequestParam String query) {
    NaverSearchMovieAggregate aggregate = naverSearchMovieService.searchMovies(query);

    return ExternalMovieListResponse.of(aggregate);
  }
}
