package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.entity.UserSession;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserSessionCreateService {

  private final UserSessionRepository userSessionRepository;

  @Transactional
  public UserSession create(User user) {
    return userSessionRepository.save(UserSession.of(user));
  }
}
