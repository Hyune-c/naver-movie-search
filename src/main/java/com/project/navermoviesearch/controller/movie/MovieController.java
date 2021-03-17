package com.project.navermoviesearch.controller.movie;

import com.project.navermoviesearch.controller.movie.response.MovieResponse;
import com.project.navermoviesearch.movie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "영화")
@RequestMapping("/movies")
@RequiredArgsConstructor
@RestController
public class MovieController {

  private final MovieService movieService;

  @Operation(summary = "검색")
  @GetMapping
  public List<MovieResponse> searchAll(
      @Parameter(description = "제목") @RequestParam String title) {
    return movieService.search(title).stream()
        .map(MovieResponse::of)
        .collect(Collectors.toList());
  }
}
