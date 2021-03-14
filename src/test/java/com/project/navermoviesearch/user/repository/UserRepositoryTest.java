package com.project.navermoviesearch.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.QueryDslConfig;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@DisplayName("회원 Repository")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Import({Jackson2ObjectMapperFactoryBean.class, QueryDslConfig.class})
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @DisplayName("[성공] 추가")
  @Test
  public void create() {
    // given
    UserEntity user = UserEntity.of("choi@gmail", "choiroot");

    // when
    userRepository.save(user);

    // then
    assertThat(userRepository.findById(user.getId())).isNotNull();
    assertThat(user.getCreatedAt()).isNotNull();
    log.info("### createdAt: {}", user.getCreatedAt());
    assertThat(user.getUpdatedAt()).isNotNull();
    log.info("### updatedAt: {}", user.getUpdatedAt());
  }
}
