package com.project.navermoviesearch.config.handler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

  private String code;
  private String message;
  private LocalDateTime time;
  private List<FieldError> errors;


  private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
    this.code = errorCode.name();
    this.message = errorCode.getReason();
    this.time = LocalDateTime.now();
    this.errors = errors;
  }

  private ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.name();
    this.message = errorCode.getReason();
    this.time = LocalDateTime.now();
    this.errors = new ArrayList<>();
  }


  public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(ErrorCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(MethodArgumentTypeMismatchException ex) {
    String value = (ex.getValue() == null) ? "" : ex.getValue().toString();
    List<FieldError> errors = List.of(FieldError.of(ex.getName(), value, ex.getErrorCode()));
    return new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
  }

  public static ErrorResponse of(MissingServletRequestParameterException ex) {
    List<FieldError> errors = List.of(FieldError.of(ex.getParameterName(), null, "Not exist"));
    return new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
  }

  public static ErrorResponse of(ConstraintViolationException ex) {
    List<FieldError> errors = ex.getConstraintViolations()
        .stream()
        .map(violation ->
            FieldError.of(getPropertyName(violation.getPropertyPath().toString()), null, violation.getMessage()))
        .collect(Collectors.toList());
    return new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
  }

  private static String getPropertyName(String propertyPath) {
    return propertyPath.substring(propertyPath.lastIndexOf('.') + 1); // 전체 속성 경로에서 속성 이름만 가져옵니다.
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class FieldError {

    private String field;
    private String value;
    private String reason;

    private FieldError(String field, String value, String reason) {
      this.field = field;
      this.value = value;
      this.reason = reason;
    }

    public static FieldError of(String field, String value, String reason) {
      return new FieldError(field, value, reason);
    }

    private static List<FieldError> of(BindingResult bindingResult) {
      return bindingResult.getFieldErrors()
          .stream()
          .map(error -> new FieldError(
              error.getField(),
              (error.getRejectedValue() == null) ? "" : error.getRejectedValue().toString(),
              error.getDefaultMessage()))
          .collect(Collectors.toList());
    }
  }
}
