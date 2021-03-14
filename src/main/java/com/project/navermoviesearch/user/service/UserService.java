package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserCreateService userCreateService;
  private final UserLoginService userLoginService;

  public long create(String loginId, String password) {
    return userCreateService.create(loginId, password);
  }

  public UserSessionDto login(String loginId, String password) {
    return userLoginService.login(loginId, password);
  }
}
