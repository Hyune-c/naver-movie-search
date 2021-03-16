package com.project.navermoviesearch.user.service.session;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.user.entity.UserSession;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSessionFindService {

  private final UserSessionRepository userSessionRepository;

  public UserSession findByUuid(UUID uuid) {
    return userSessionRepository.findByUuid(uuid)
        .orElseThrow(() -> new BusinessException(ErrorCode.USER_SESSION_NOT_EXISTS));
  }

  public UserSession findByAuthorization(String authorizationValue) {
    return findByUuid(UUID.fromString(authorizationValue));
  }
}
