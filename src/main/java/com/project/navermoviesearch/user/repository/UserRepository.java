package com.project.navermoviesearch.user.repository;

import com.project.navermoviesearch.user.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByLoginId(String loginId);
}
