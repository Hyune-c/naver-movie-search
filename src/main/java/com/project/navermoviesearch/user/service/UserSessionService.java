package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.entity.UserSessionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

  private final UserSessionCreateService userSessionCreateService;
  private final UserSessionDeleteService userSessionDeleteService;

  public UserSessionEntity create(UserEntity user) {
    return userSessionCreateService.create(user);
  }

  public void deleteAllByUserId(long userId) {
    userSessionDeleteService.deleteAllByUserId(userId);
  }
}
