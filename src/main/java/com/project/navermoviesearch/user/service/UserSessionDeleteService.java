package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionDeleteService {

  private final UserSessionRepository userSessionRepository;

  @Async
  public void deleteAllByUserId(long userId) {
    userSessionRepository.deleteAllByUserId(userId);
  }
}
