package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.controller.user.request.UserLoginRequest;
import com.project.navermoviesearch.user.dto.UserSessionDto;
import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.service.UserCreateService;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("회원 로그인 Controller")
@ContextConfiguration(initializers = TestContextInitializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class LoginTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserCreateService userCreateService;

  private String url;

  @PostConstruct
  private void postConstruct() {
    url = "/users/login";
  }

  private static Stream<Arguments> successArguments() {
    return Stream.of(
        Arguments.of("choi8608@gmail.com", "choiroot")
    );
  }

  @DisplayName("[성공]")
  @ParameterizedTest
  @MethodSource("successArguments")
  public void success(String loginId, String password) throws Exception {
    // given
    UserEntity user = UserEntity.of(loginId, password);
    userCreateService.create(user.getLoginId(), user.getPassword());

    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(loginId)
        .password(password)
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

    // then
    UserSessionDto session = (UserSessionDto) result.getRequest().getSession().getAttribute("session");
    assertThat(session.getUuid()).isNotNull();
    log.info("### getUuid(): {}", session.getUuid());
    assertThat(session.getUserId()).isNotNull();
    log.info("### getUserId(): {}", session.getUserId());
  }

  @DisplayName("[성공] 2번 연속 로그인")
  @ParameterizedTest
  @MethodSource("successArguments")
  public void success_loginAgain(String loginId, String password) throws Exception {
    // given
    UserEntity user = UserEntity.of(loginId, password);
    userCreateService.create(user.getLoginId(), user.getPassword());

    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(loginId)
        .password(password)
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult firstResult = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();
    UserSessionDto firstSession = (UserSessionDto) firstResult.getRequest().getSession().getAttribute("session");

    // when
    MvcResult secondResult = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

    // then
    UserSessionDto secondSession = (UserSessionDto) secondResult.getRequest().getSession().getAttribute("session");
    assertThat(secondSession.getUuid()).isNotNull();
    log.info("### getUuid(): {}", secondSession.getUuid());
    assertThat(secondSession.getUserId()).isNotNull();
    log.info("### getUserId(): {}", secondSession.getUserId());

    // 2번 로그인하면 세션 UUID 가 서로 달라야합니다.
    assertThat(firstSession.getUserId()).isEqualTo(secondSession.getUserId());
    assertThat(firstSession.getUuid()).isNotEqualTo(secondSession.getUuid());
  }

  private static Stream<Arguments> failedArguments() {
    return Stream.of(
        Arguments.of("choi8608@gmail.com", "choiroot")
    );
  }

  @DisplayName("[실패] 존재하지 않는 로그인 아이디")
  @ParameterizedTest
  @MethodSource("failedArguments")
  public void failed_notExistLoginId(String loginId, String password) throws Exception {
    // given
    UserEntity user = UserEntity.of(loginId, password);
    userCreateService.create(user.getLoginId(), user.getPassword());

    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(loginId + "asdf")
        .password(password)
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andReturn();

    // then
  }

  @DisplayName("[실패] 틀린 비밀번호")
  @ParameterizedTest
  @MethodSource("failedArguments")
  public void failed_wrongPassword(String loginId, String password) throws Exception {
    // given
    UserEntity user = UserEntity.of(loginId, password);
    userCreateService.create(user.getLoginId(), user.getPassword());

    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(loginId)
        .password(password + "asd")
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andReturn();

    // then
  }
}



