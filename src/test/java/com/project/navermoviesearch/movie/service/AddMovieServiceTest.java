package com.project.navermoviesearch.movie.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieGenreRepository;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[Service] 영화 추가")
@ContextConfiguration(initializers = TestContextInitializer.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class AddMovieServiceTest {

  @Autowired
  private AddMovieService addMovieService;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieGenreRepository movieGenreRepository;

  @DisplayName("[성공]")
  @Test
  public void success() {
    //given

    // when
    Movie movie = addMovieService.addMovie("살아있다");

    // then
    log.info("###");
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieGenreRepository.findAllByMovie_id(movie.getId()).size()).isLessThan(5);
  }
}
