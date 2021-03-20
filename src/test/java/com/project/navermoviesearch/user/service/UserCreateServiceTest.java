package com.project.navermoviesearch.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 회원 가입 ")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class UserCreateServiceTest {

  @Autowired
  private UserCreateService createService;

  private static Stream<Arguments> failedArguments() {
    return Stream.of(
        Arguments.of("choi8608@gmail.com", "choiroot")
    );
  }

  @DisplayName("[실패] 이미 존재하는 로그인 아이디")
  @ParameterizedTest
  @MethodSource("failedArguments")
  public void failed_existLoginId(String loginId, String password) {
    // given
    createService.create(loginId, password);

    // when
    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> createService.create(loginId, password));

    // then
    assertThat(exception.getMessage()).isEqualTo("이미 존재하는 로그인 ID");
  }
}
