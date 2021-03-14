package com.project.navermoviesearch.user.dto;

import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.entity.UserSessionEntity;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSessionAggregate {

  private final UUID uuid;
  private final UserEntity user;

  public static UserSessionAggregate of(UserSessionEntity session) {
    return UserSessionAggregate.builder()
        .uuid(session.getUuid())
        .user(session.getUser())
        .build();
  }
}
