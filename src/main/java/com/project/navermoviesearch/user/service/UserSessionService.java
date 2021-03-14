package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.dto.UserSessionDto;
import com.project.navermoviesearch.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

  private final UserSessionCreateService userSessionCreateService;

  public UserSessionDto create(UserEntity user) {
    return userSessionCreateService.create(user);
  }
}
