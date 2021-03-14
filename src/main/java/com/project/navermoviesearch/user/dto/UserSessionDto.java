package com.project.navermoviesearch.user.dto;

import com.project.navermoviesearch.user.entity.UserSessionEntity;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSessionDto {

  private final UUID uuid;
  private final Long userId;

  public static UserSessionDto of(UserSessionEntity session) {
    return UserSessionDto.builder()
        .uuid(session.getUuid())
        .userId(session.getUserId())
        .build();
  }
}
