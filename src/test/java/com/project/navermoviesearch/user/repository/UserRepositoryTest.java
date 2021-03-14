package com.project.navermoviesearch.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.QueryDslConfig;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.entity.UserEntity;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@ContextConfiguration(initializers = TestContextInitializer.class)
@Import({Jackson2ObjectMapperFactoryBean.class, QueryDslConfig.class})
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  public void test() {
    // given

    // when
    List<UserEntity> userEntityList = userRepository.findAll();

    // then
    assertThat(userEntityList.size()).isEqualTo(0);
  }
}
