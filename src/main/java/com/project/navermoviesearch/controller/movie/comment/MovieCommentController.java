package com.project.navermoviesearch.controller.movie.comment;

import com.project.navermoviesearch.config.annotation.LoginUser;
import com.project.navermoviesearch.controller.movie.comment.request.MovieCommentCreateRequest;
import com.project.navermoviesearch.controller.movie.comment.request.MovieCommentResponse;
import com.project.navermoviesearch.movie.comment.service.MovieCommentService;
import com.project.navermoviesearch.movie.service.MovieService;
import com.project.navermoviesearch.user.entity.UserSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "영화 코멘트")
@RequestMapping("/movies/{movieId}/comments")
@RequiredArgsConstructor
@RestController
public class MovieCommentController {

  private final MovieService movieService;
  private final MovieCommentService movieCommentService;

  @Operation(summary = "추가")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Long create(
      @LoginUser UserSession session,
      @Parameter(description = "영화 ID") @PathVariable Long movieId,
      @RequestBody @Valid MovieCommentCreateRequest request) {
    return movieService.createComment(movieId, session.getUser(), request.getContent());
  }

  @Operation(summary = "목록")
  @GetMapping
  public Slice<MovieCommentResponse> findList(
      @LoginUser(required = false) UserSession session,
      @Parameter(description = "영화 ID") @PathVariable Long movieId,
      @RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
      @RequestParam(defaultValue = "10") @Positive Integer size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return movieCommentService.findByMovieId(pageRequest, movieId)
        .map(MovieCommentResponse::of);
  }
}
