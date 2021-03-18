package com.project.navermoviesearch.controller.movie.comment.request;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class MovieCommentResponse {

  @Schema(name = "영화 코멘트 ID")
  private Long id;

  @Schema(name = "내용")
  private String content;

  @Schema(name = "최종 수정일")
  private LocalDateTime updatedAt;

  public static MovieCommentResponse of(MovieComment comment) {
    return MovieCommentResponse.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .updatedAt(comment.getUpdatedAt())
        .build();
  }
}
