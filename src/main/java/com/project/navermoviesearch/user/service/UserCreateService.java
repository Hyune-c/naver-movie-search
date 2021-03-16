package com.project.navermoviesearch.user.service;

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
      throw new RuntimeException("이미 존재하는 로그인 ID");
    }

    User user = User.of(loginId, password);
    return userRepository.save(user).getId();
  }
}
