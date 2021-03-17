package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.user.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSessionDeleteService {

  private final UserSessionRepository userSessionRepository;

  @Transactional
  public void deleteAllByUserId(long userId) {
    userSessionRepository.deleteAllByUserId(userId);
  }
}
