package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.controller.user.request.UserLoginRequest;
import com.project.navermoviesearch.user.dto.UserSessionAttribute;
import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import com.project.navermoviesearch.util.TestUtil;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
  private UserRepository userRepository;

  @Autowired
  private UserSessionRepository userSessionRepository;

  private String url;
  private UserEntity testUser;

  @BeforeEach
  public void postConstruct() {
    url = "/users/login";

    testUser = UserEntity.of("test@test.com", "choiroot");
    testUser.setCreatedAt(LocalDateTime.now());
    testUser.setUpdatedAt(LocalDateTime.now());
    userRepository.save(testUser);
  }

  @AfterEach
  public void afterEach() {
    userRepository.deleteAll();
  }

  @DisplayName("[성공]")
  @Test
  public void success() throws Exception {
    // given
    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(testUser.getLoginId())
        .password(testUser.getPassword())
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
    UUID uuid = TestUtil.mvcResultToObject(result, UUID.class);
    assertThat(userSessionRepository.findByUuid(uuid)).isNotNull();
  }

  @DisplayName("[성공] 2번 연속 로그인")
  @Test
  public void success_loginAgain() throws Exception {
    // given
    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(testUser.getLoginId())
        .password(testUser.getPassword())
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);
    MvcResult firstResult = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();
    UUID firstUuid = TestUtil.mvcResultToObject(firstResult, UUID.class);
    assertThat(userSessionRepository.findByUuid(firstUuid)).isNotNull();

    // when
    MvcResult secondResult = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

    // then
    UUID secondUuid = TestUtil.mvcResultToObject(secondResult, UUID.class);
    assertThat(userSessionRepository.findByUuid(secondUuid)).isNotNull();

    // 2번 로그인하면 세션 UUID 가 서로 달라야합니다.
    assertThat(firstUuid).isNotEqualTo(secondUuid);
  }

  @DisplayName("[실패] 존재하지 않는 로그인 아이디")
  @Test
  public void failed_notExistLoginId() throws Exception {
    // given
    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(testUser.getLoginId() + "asdf")
        .password(testUser.getPassword())
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
  @Test
  public void failed_wrongPassword() throws Exception {
    // given
    UserLoginRequest request = UserLoginRequest.builder()
        .loginId(testUser.getLoginId())
        .password(testUser.getPassword() + "asd")
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



