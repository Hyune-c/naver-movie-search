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
  USER_NOT_EXISTS("100003", HttpStatus.NOT_FOUND, "존재하지 않는 회원"),

  // 회원 세션
  USER_SESSION_NOT_EXISTS("101001", HttpStatus.NOT_FOUND, "존재하지 않는 세션"),

  // 외부 서비스
  // 네이버 영화검색
  EXTERNAL_ILLEGAL_DATA("809400", HttpStatus.CONFLICT, "외부 서비스 데이터 처리 실패"),
  EXTERNAL_UNKNOWN_FAILED("809500", HttpStatus.CONFLICT, "알수 없는 외부 서비스 실패"),

  // 기타
  BAD_REQUEST("990400", HttpStatus.BAD_REQUEST, "잘못된 입력 값"),
  FORBIDDEN("990403", HttpStatus.FORBIDDEN, "권한 없음"),
  UNKNOWN("990500", HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러");

  private final String code;
  private final HttpStatus status;
  private final String reason;
}
