package com.project.navermoviesearch.user.service;

import com.project.navermoviesearch.user.dto.UserSessionDto;
import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.entity.UserSessionEntity;
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
  public UserSessionDto create(UserEntity user) {
    userSessionRepository.deleteAllByUserId(user.getId());
    UserSessionEntity session = UserSessionEntity.of(user);

    return UserSessionDto.of(userSessionRepository.save(session));
  }
}
