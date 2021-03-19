package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.service.session.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserLogoutService {

  private final UserSessionService userSessionService;

  @Async
  public void logout(long userId) {
    userSessionService.deleteAllByUserId(userId);
  }
}
