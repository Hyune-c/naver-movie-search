package com.project.navermoviesearch.controller.movie;

import com.project.navermoviesearch.code.SortKey;
import com.project.navermoviesearch.controller.movie.response.MovieResponse;
import com.project.navermoviesearch.movie.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
  public Slice<MovieResponse> search(
      @Parameter(description = "제목") @RequestParam(defaultValue = "") String title,
      @RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
      @RequestParam(defaultValue = "10") @Positive Integer size,
      @RequestParam(required = false) SortKey.Movie sortKey) {
    PageRequest pageRequest = (sortKey == null)
        ? PageRequest.of(page, size)
        : PageRequest.of(page, size, Sort.by(sortKey.name()));

    return movieService.search(pageRequest, title)
        .map(MovieResponse::of);
  }
}
