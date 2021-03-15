package com.project.navermoviesearch.user.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserCreateService userCreateService;
  private final UserLoginService userLoginService;
  private final UserLogoutService userLogoutService;

  public long create(String loginId, String password) {
    return userCreateService.create(loginId, password);
  }

  public UUID login(String loginId, String password) {
    return userLoginService.login(loginId, password);
  }

  public void logout(long userId) {
    userLogoutService.logout(userId);
  }
}
