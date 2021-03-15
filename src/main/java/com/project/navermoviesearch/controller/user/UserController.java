package com.project.navermoviesearch.controller.user;

import com.project.navermoviesearch.controller.user.request.UserCreateRequest;
import com.project.navermoviesearch.controller.user.request.UserLoginRequest;
import com.project.navermoviesearch.user.dto.UserSessionAttribute;
import com.project.navermoviesearch.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원")
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

  private final UserService userService;

  @Operation(summary = "가입")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Long create(@RequestBody @Valid UserCreateRequest request) {
    return userService.create(request.getLoginId(), request.getPassword());
  }

  @Operation(summary = "로그인")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/login")
  public UUID login(@RequestBody @Valid UserLoginRequest request) {
    return userService.login(request.getLoginId(), request.getPassword());
  }

  @Operation(summary = "로그아웃")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PostMapping("/logout")
  public void logout(HttpSession httpSession) {
    UserSessionAttribute session = (UserSessionAttribute) httpSession.getAttribute("session");
    userService.logout(session.getUserId());
    httpSession.invalidate();
  }
}
