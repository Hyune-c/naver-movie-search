package com.project.navermoviesearch.controller.movie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.project.navermoviesearch.controller.movie.response.MovieResponse;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import com.project.navermoviesearch.util.TestUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[web] 영화 목록")
@Sql({"classpath:database/initUser.sql", "classpath:database/initMovie.sql"})
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MovieRepository movieRepository;

  private List<User> userList;
  private List<Movie> movieList;
  private User user;
  private Movie movie;

  private String url;

  @BeforeEach
  public void beforeEach() {
    userList = userRepository.findAll();
    movieList = movieRepository.findAll();
    user = userList.get(0);
    movie = movieList.get(0);

    url = "/movies";
  }

  @DisplayName("[성공] 검색")
  @Test
  public void search() throws Exception {
    // given
    RequestBuilder requestBuilder = get(url);

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    // then
    List<MovieResponse> responseList = TestUtil.pageMvcResultToList(result, MovieResponse.class);
    assertThat(responseList.size()).isGreaterThan(0);
  }

  @DisplayName("[성공] 평점 기준 검색")
  @Test
  public void search_withRating() throws Exception {
    // given
    RequestBuilder requestBuilder = get(url);

    // when
    MvcResult result = mockMvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    // then
    List<MovieResponse> responseList = TestUtil.pageMvcResultToList(result, MovieResponse.class);
    assertThat(responseList.size()).isGreaterThan(0);
  }
}
