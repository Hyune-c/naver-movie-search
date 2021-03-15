package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.entity.UserSessionEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionService {

  private final UserSessionCreateService userSessionCreateService;
  private final UserSessionDeleteService userSessionDeleteService;
  private final UserSessionFindService userSessionFindService;

  public UUID create(UserEntity user) {
    return userSessionCreateService.create(user).getUuid();
  }

  public void deleteAllByUserId(long userId) {
    userSessionDeleteService.deleteAllByUserId(userId);
  }

  public UserSessionEntity findByUuid(UUID uuid) {
    return userSessionFindService.findByUuid(uuid);
  }

  public UserSessionEntity findByAuthorization(String authorization) {
    return userSessionFindService.findByAuthorization(authorization);
  }
}
