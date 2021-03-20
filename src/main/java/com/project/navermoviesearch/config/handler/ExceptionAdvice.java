package com.project.navermoviesearch.config.handler;

import com.project.navermoviesearch.config.handler.exception.BusinessException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  protected ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
    log.error("### handleHttpRequestMethodNotSupportedException", ex);
    return ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);
  }

  /*
    필요한 param 값이 들어오지 않았을 때 발생합니다.
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    log.error("### handleMissingServletRequestParameterException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  /*
    type 이 일치하지 않아 binding 못할 경우 발생
    주로 @RequestParam enum 으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.error("### handleMethodArgumentTypeMismatchException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  /*
    javax.validation 을 통과하지 못하면 에러가 발생한다.
    주로 @NotBlank, @NotEmpty, @NotNull 에서 발생
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
    log.error("### handleConstraintViolationException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("### handleIllegalArgumentException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("### handleMethodArgumentNotValidException", ex);
    return ErrorResponse.of(ErrorCode.BAD_REQUEST);
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
    log.error("### handleBusinessException", ex);
    return new ResponseEntity<>(ErrorResponse.of(ex.getErrorCode()), ex.getErrorCode().getStatus());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ErrorResponse handleException(Exception ex) {
    log.error("### handleException", ex);
    return ErrorResponse.of(ErrorCode.UNKNOWN);
  }
}
