package com.project.navermoviesearch.config.handler;

import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private String code;
  private String message;
  private OffsetDateTime time;

  private ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getReason();
    this.time = OffsetDateTime.now();
  }

  public static ErrorResponse of(ErrorCode code) {
    return new ErrorResponse(code);
  }
}
