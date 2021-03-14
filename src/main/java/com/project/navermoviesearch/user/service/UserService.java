package com.project.navermoviesearch.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserCreateService userCreateService;

  public long create(String loginId, String password) {
    return userCreateService.create(loginId, password);
  }
}
