package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.entity.UserSession;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

  private final UserSessionCreateService userSessionCreateService;
  private final UserSessionDeleteService userSessionDeleteService;
  private final UserSessionFindService userSessionFindService;

  /*
    이미 로그인 되어 있는 세션이 있다면 삭제합니다.
   */
  public UUID create(User user) {
    deleteAllByUserId(user.getId());
    return userSessionCreateService.create(user).getUuid();
  }

  public void deleteAllByUserId(long userId) {
    userSessionDeleteService.deleteAllByUserId(userId);
  }

  public UserSession findByUuid(UUID uuid) {
    return userSessionFindService.findByUuid(uuid);
  }

  public UserSession findByAuthorization(String authorization) {
    return userSessionFindService.findByAuthorization(authorization);
  }
}
