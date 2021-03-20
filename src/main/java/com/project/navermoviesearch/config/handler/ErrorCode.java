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

  // 영화
  MOVIE_ALREADY_EXISTS("400001", HttpStatus.CONFLICT, "이미 존재하는 영화"),
  MOVIE_FAILED_ADD("400002", HttpStatus.CONFLICT, "영화 추가 실패"),

  // 코멘트
  COMMENTS_NOT_EXISTS("500001", HttpStatus.CONFLICT, "존재하지 않는 코멘트"),

  // 외부 서비스
  // 네이버 영화검색
  EXTERNAL_ILLEGAL_DATA("809400", HttpStatus.CONFLICT, "외부 서비스 데이터 처리 실패"),
  EXTERNAL_UNKNOWN_FAILED("809500", HttpStatus.CONFLICT, "알수 없는 외부 서비스 실패"),
  EXTERNAL_NOT_EXIST_MOVIE("809401", HttpStatus.CONFLICT, "외부 서비스에 존재하지 않는 영화"),

  // 기타
  BAD_REQUEST("990400", HttpStatus.BAD_REQUEST, "잘못된 입력 값"),
  UNAUTHORIZED("990401", HttpStatus.UNAUTHORIZED, "인증 실패"),
  FORBIDDEN("990403", HttpStatus.FORBIDDEN, "권한 없음"),
  NOT_FOUND("990404", HttpStatus.NOT_FOUND, "찾을 수 없음"),
  METHOD_NOT_ALLOWED("990405", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메소드"),
  UNKNOWN("990500", HttpStatus.INTERNAL_SERVER_ERROR, "알수 없는 서버 에러");

  private final String code;
  private final HttpStatus status;
  private final String reason;
}
