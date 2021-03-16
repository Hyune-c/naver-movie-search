package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.entity.UserSession;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionCreateService {

  private final UserSessionRepository userSessionRepository;

  /*
    이미 로그인 되어 있는 세션이 있다면 삭제합니다.
   */
  public UserSession create(User user) {
    userSessionRepository.deleteAllByUserId(user.getId());
    return userSessionRepository.save(UserSession.of(user));
  }
}
