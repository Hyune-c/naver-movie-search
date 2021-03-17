package com.project.navermoviesearch.config.handler;

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
public class ErrorResponse {

  private String code;
  private String message;
  private LocalDateTime time;

  public static ErrorResponse of(ErrorCode code) {
    return ErrorResponse.builder()
        .code(code.getCode())
        .message(code.getReason())
        .time(LocalDateTime.now())
        .build();
  }
}
