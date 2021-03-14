package com.project.navermoviesearch.user.session.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.QueryDslConfig;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.entity.UserSessionEntity;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
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
@DisplayName("회원 세션 Repository")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Import({Jackson2ObjectMapperFactoryBean.class, QueryDslConfig.class})
@ActiveProfiles("test")
@DataJpaTest
class UserSessionRepositoryTest {

  @Autowired
  private UserSessionRepository userSessionRepository;

  @DisplayName("[성공] 추가")
  @Test
  public void create() {
    // given
    long userId = 10L;
    UserSessionEntity userSession = new UserSessionEntity();
    userSession.setUserId(userId);

    // when
    userSessionRepository.save(userSession);

    // then
    assertThat(userSessionRepository.findByUuid(userSession.getUuid())).isNotNull();
    log.info("### uuid: {}", userSession.getUuid());
    log.info("### userId: {}", userSession.getUserId());
  }
}

