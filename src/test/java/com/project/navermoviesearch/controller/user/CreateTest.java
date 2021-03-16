package com.project.navermoviesearch.controller.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.controller.user.request.UserCreateRequest;
import com.project.navermoviesearch.user.repository.UserRepository;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("회원 가입 Controller")
@ContextConfiguration(initializers = TestContextInitializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class CreateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  private String url;

  @PostConstruct
  private void postConstruct() {
    url = "/users";
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
    UserCreateRequest request = UserCreateRequest.builder()
        .loginId(loginId)
        .password(password)
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();

    // then
    assertThat(userRepository.findByLoginId(loginId)).isNotNull();
  }


  private static Stream<Arguments> failedArguments() {
    return Stream.of(
        Arguments.of("choi8608gmail.com", "choiroot"),
        Arguments.of("@choi8608gmail.com", "choiroot"),
        Arguments.of("", "choiroot"),
        Arguments.of("choi8608@gmailcom", "")
    );
  }

  @DisplayName("[실패]")
  @ParameterizedTest
  @MethodSource("failedArguments")
  public void failed(String loginId, String password) throws Exception {
    // given
    UserCreateRequest request = UserCreateRequest.builder()
        .loginId(loginId)
        .password(password)
        .build();
    RequestBuilder requestBuilder = post(url)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON);

    // when
    mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andReturn();

    // then
  }
}
