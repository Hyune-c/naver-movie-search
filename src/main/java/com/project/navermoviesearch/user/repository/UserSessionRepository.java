package com.project.navermoviesearch.user.repository;

import com.project.navermoviesearch.user.entity.UserSession;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByUuid(UUID uuid);

  void deleteAllByUserId(Long userId);
}
