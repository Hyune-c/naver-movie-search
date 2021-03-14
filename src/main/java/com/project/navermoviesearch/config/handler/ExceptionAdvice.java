package com.project.navermoviesearch.config.handler;

import com.project.navermoviesearch.config.handler.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ex.printStackTrace();
    log.error("handleMethodArgumentNotValidException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    ex.printStackTrace();
    log.error("handleBusinessException: {}", ex.getMessage());
    return new ResponseEntity<>(ErrorResponse.of(ex.getErrorCode()), ex.getErrorCode().getStatus());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ErrorResponse handleException(Exception ex) {
    ex.printStackTrace();
    log.error("handleException: {}", ex.getMessage());
    return ErrorResponse.of(ErrorCode.UNKNOWN);
  }
}
