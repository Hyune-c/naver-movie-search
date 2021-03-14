package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.user.dto.UserSessionAttribute;
import com.project.navermoviesearch.user.entity.UserEntity;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import com.project.navermoviesearch.user.service.UserSessionService;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("회원 로그아웃 Controller")
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
  private UserEntity testUser;

  @PostConstruct
  private void postConstruct() {
    url = "/users/logout";

    testUser = UserEntity.of("test@test.com", "choiroot");
    testUser.setCreatedAt(LocalDateTime.now());
    testUser.setUpdatedAt(LocalDateTime.now());
    userRepository.save(testUser);
  }

  @DisplayName("[성공]")
  @Test
  public void success() throws Exception {
    // given
    UserSessionAttribute session = UserSessionAttribute.of(userSessionService.create(testUser));
    assertThat(userSessionRepository.findByUuid(session.getUuid())).isPresent();

    RequestBuilder requestBuilder = post(url)
        .sessionAttr("session", session);

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

    // then
    assertThat(result.getRequest().getSession().getAttribute("session")).isNull();
    assertThat(userSessionRepository.findByUuid(session.getUuid())).isEmpty();
  }
}



