package com.project.navermoviesearch.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.controller.moviesearch.response.MovieListResponse;
import com.project.navermoviesearch.util.TestUtil;
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

@Slf4j
@DisplayName("Naver 영화 검색")
@ContextConfiguration(initializers = TestContextInitializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class MovieSearchControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private String url;

  @PostConstruct
  private void postConstruct() {
    url = "/movies";
  }

  @DisplayName("성공")
  @Test
  public void success() throws Exception {
    // given
    String query = "살아있다";
    GenreCode genre = GenreCode.DRAMA;
    RequestBuilder requestBuilder = get(url)
        .param("query", query)
        .param("genre", genre.name());

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    // then
    MovieListResponse response = TestUtil.mvcResultToObject(result, MovieListResponse.class);
    assertThat(response.getMovies().size()).isGreaterThan(0);
    response.getMovies().forEach(movie -> assertThat(movie.getTitle()).isNotNull());
  }
}
