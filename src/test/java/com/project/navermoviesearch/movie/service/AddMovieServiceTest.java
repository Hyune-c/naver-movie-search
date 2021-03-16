package com.project.navermoviesearch.movie.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@DisplayName("[Service] 영화 추가")
@ContextConfiguration(initializers = TestContextInitializer.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
class AddMovieServiceTest {

  @Autowired
  private AddMovieService addMovieService;

  @Autowired
  private MovieRepository movieRepository;

  @AfterEach
  public void afterEach() throws InterruptedException {
    Thread.sleep(500L);
  }

  public static String[] existMovieTitleFromNaver() {
    return new String[]{"수사", "승리", "남산", "부장", "부산", "제주", "학교", "전쟁", "군"};
  }

  @DisplayName("[성공]")
  @ParameterizedTest
  @MethodSource("existMovieTitleFromNaver")
  public void success(String title) {
    //given

    // when
    List<Movie> movies = addMovieService.addMovie(title);

    // then
    movies.forEach(movie -> assertThat(movieRepository.findById(movie.getId())).isPresent());
  }

  @DisplayName("[성공] 이미 존재하는 영화")
  @ParameterizedTest
  @MethodSource("existMovieTitleFromNaver")
  public void success_alreadyExists(String title) {
    //given
    addMovieService.addMovie(title);
    int beforeSize = movieRepository.findAll().size();
    assertThat(beforeSize).isGreaterThan(0);

    // when
    addMovieService.addMovie(title);

    // then
    int afterSize = movieRepository.findAll().size();
    assertThat(beforeSize).isEqualTo(afterSize);
  }

  public static String[] notExistMovieTitleFromNaver() {
    return new String[]{"없겠지요", "이게있을리가"};
  }

  @DisplayName("[실패] Naver 에 존재하지 않는 영화 이름")
  @ParameterizedTest
  @MethodSource("notExistMovieTitleFromNaver")
  public void failed(String title) {
    //given

    // when
    List<Movie> movieList = addMovieService.addMovie(title);

    // then
    assertThat(movieList.size()).isEqualTo(0);
  }
}
