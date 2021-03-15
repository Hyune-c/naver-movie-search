package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.service.session.UserSessionService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserLoginService {

  private final UserRepository userRepository;
  private final UserSessionService userSessionService;

  public UUID login(String loginId, String password) {
    return userRepository.findByLoginId(loginId)
        .map(user -> {
          validatePassword(password, user);

          return userSessionService.create(user);
        }).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_EXISTS_OR_WRONG_PASSWORD));
  }

  private void validatePassword(String password, UserEntity user) {
    if (!password.equals(user.getPassword())) {
      throw new BusinessException(ErrorCode.USER_NOT_EXISTS_OR_WRONG_PASSWORD);
    }
  }
}
