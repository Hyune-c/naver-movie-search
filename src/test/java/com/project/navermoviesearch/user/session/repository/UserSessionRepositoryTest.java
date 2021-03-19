package com.project.navermoviesearch.user.session.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.QueryDslConfig;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.entity.UserSession;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@DisplayName("[repository] 회원 세션")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Import({Jackson2ObjectMapperFactoryBean.class, QueryDslConfig.class})
@ActiveProfiles("test")
@DataJpaTest
class UserSessionRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserSessionRepository userSessionRepository;

  private static Stream<Arguments> createArguments() {
    return Stream.of(
        Arguments.of("choi8608@gmail.com", "choiroot")
    );
  }

  @DisplayName("[성공] 추가")
  @ParameterizedTest
  @MethodSource("createArguments")
  public void create(String loginId, String password) {
    // given
    User user = User.of(loginId, password);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    UserSession userSession = UserSession.of(user);

    // when
    userSessionRepository.save(userSession);

    // then
    assertThat(userSessionRepository.findByUuid(userSession.getUuid())).isNotNull();
    log.info("### uuid: {}", userSession.getUuid());
  }
}

