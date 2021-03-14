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
public class UserSessionAttribute {

  private final UUID uuid;
  private final Long userId;

  public static UserSessionAttribute of(UserSessionAggregate session) {
    return UserSessionAttribute.builder()
        .uuid(session.getUuid())
        .userId(session.getUser().getId())
        .build();
  }

  public static UserSessionAttribute of(UserSessionEntity session) {
    return UserSessionAttribute.builder()
        .uuid(session.getUuid())
        .userId(session.getUser().getId())
        .build();
  }
}
