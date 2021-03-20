package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.service.session.UserSessionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserCreateService userCreateService;
  private final UserLoginService userLoginService;

  private final UserSessionService userSessionService;

  public long create(String loginId, String password) {
    return userCreateService.create(loginId, password);
  }

  public UUID login(String loginId, String password) {
    return userLoginService.login(loginId, password);
  }

  @Async
  public void logout(long userId) {
    userSessionService.deleteAllByUserId(userId);
  }
}
