package com.project.navermoviesearch.controller.movie;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "영화")
@RequestMapping("/movies")
@RequiredArgsConstructor
@RestController
public class MovieController {

  @Operation(summary = "검색")
  @GetMapping
  public void search() {
  }
}
