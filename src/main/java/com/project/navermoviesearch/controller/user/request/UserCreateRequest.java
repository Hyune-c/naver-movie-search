package com.project.navermoviesearch.controller.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class UserCreateRequest {

  @Schema(description = "로그인 이메일 주소")
  @Email
  @NotEmpty
  private String loginId;

  @Schema(description = "비밀번호")
  @NotEmpty
  private String password;
}

