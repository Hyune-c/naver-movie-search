package com.project.navermoviesearch.user.repository;

import com.project.navermoviesearch.user.entity.UserSessionEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

  Optional<UserSessionEntity> findByUuid(UUID uuid);

  void deleteAllByUserId(Long userId);
}
