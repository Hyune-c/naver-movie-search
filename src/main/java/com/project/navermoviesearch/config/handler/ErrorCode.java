package com.project.navermoviesearch.config.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

  // 회원
  USER_EXISTS_LOGIN_ID("100001", HttpStatus.CONFLICT, "이미 존재하는 로그인 ID"),
  USER_NOT_EXISTS_OR_WRONG_PASSWORD("100002", HttpStatus.UNAUTHORIZED, "존재하지 않는 회원, 또는 틀린 비밀번호"),

  // 기타
  BAD_REQUEST("990400", HttpStatus.BAD_REQUEST, "잘못된 입력 값"),
  UNKNOWN("990500", HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러");

  private final String code;
  private final HttpStatus status;
  private final String reason;
}
