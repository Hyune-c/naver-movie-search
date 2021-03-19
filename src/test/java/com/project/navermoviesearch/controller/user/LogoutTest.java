package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import com.project.navermoviesearch.user.service.session.UserSessionService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[web] 회원 로그아웃 ")
@ContextConfiguration(initializers = TestContextInitializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class LogoutTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserSessionService userSessionService;

  @Autowired
  private UserSessionRepository userSessionRepository;

  @Autowired
  private UserRepository userRepository;

  private String url;
  private String authorizationKey;
  private User testUser;

  @BeforeEach
  public void beforeEach() {
    url = "/users/logout";

    authorizationKey = "Authorization";

    testUser = User.of("test@test.com", "choiroot");
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
    UUID uuid = userSessionService.create(testUser);
    assertThat(userSessionRepository.findByUuid(uuid)).isPresent();

    RequestBuilder requestBuilder = delete(url)
        .header(authorizationKey, uuid);

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

    // then
    assertThat(userSessionRepository.findByUuid(uuid)).isEmpty();
  }

  @DisplayName("[실패]")
  @Test
  public void failed_wrongAuthorization() throws Exception {
    // given
    UUID uuid = userSessionService.create(testUser);
    assertThat(userSessionRepository.findByUuid(uuid)).isPresent();

    RequestBuilder requestBuilder = delete(url)
        .header(authorizationKey, uuid.toString() + "1");

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andReturn();

    // then
    assertThat(userSessionRepository.findByUuid(uuid)).isPresent();
  }
}



