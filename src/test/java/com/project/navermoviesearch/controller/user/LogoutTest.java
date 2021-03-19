package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.entity.UserSession;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.user.repository.UserSessionRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[web] 회원 로그아웃 ")
@Sql({"classpath:database/initUser.sql"})
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class LogoutTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserSessionRepository userSessionRepository;

  @Autowired
  private UserRepository userRepository;

  private String url;
  private String authorizationKey;

  private List<User> userList;
  User user;

  @BeforeEach
  public void beforeEach() {
    url = "/users/logout";
    authorizationKey = "Authorization";

    userList = userRepository.findAll();
    user = userList.get(0);
  }

  @DisplayName("[실패]")
  @Test
  public void failed_wrongAuthorization() throws Exception {
    // given
    UserSession session = userSessionRepository.save(UserSession.of(user));
    assertThat(session.getUuid()).isNotNull();

    RequestBuilder requestBuilder = delete(url)
        .header(authorizationKey, session.getUuid().toString() + "1");

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isForbidden())
        .andReturn();

    // then
    assertThat(userSessionRepository.findByUuid(session.getUuid())).isPresent();
  }
}



