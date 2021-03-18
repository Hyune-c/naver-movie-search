package com.project.navermoviesearch.user.service;

import static com.project.navermoviesearch.config.handler.ErrorCode.USER_EXISTS_LOGIN_ID;

import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCreateService {

  private final UserRepository userRepository;

  public long create(String loginId, String password) throws RuntimeException {
    if (userRepository.existsByLoginId(loginId)) {
      throw new BusinessException(USER_EXISTS_LOGIN_ID);
    }

    User user = User.of(loginId, password);
    return userRepository.save(user).getId();
  }
}
