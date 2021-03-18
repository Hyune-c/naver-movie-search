package com.project.navermoviesearch.movie.rating.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.rating.repository.MovieRatingRepository;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 영화 평점")
@ContextConfiguration(initializers = TestContextInitializer.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MovieRatingServiceTest {

  @Autowired
  private MovieRatingService movieRatingService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieRatingRepository movieRatingRepository;

  User user;
  Movie movie;

  @BeforeEach
  public void beforeEach() {
    user = userRepository.save(User.of("test@test.com", "root"));
    movie = movieRepository.save(Movie.of("test movie title"));
  }

  @AfterEach
  public void afterEach() {
    userRepository.deleteAll();
    movieRepository.deleteAll();
  }

  @DisplayName("[성공] 추가")
  @Test
  public void create() {
    // given
    int score = 4;

    // when
    movieRatingService.createOrUpdate(movie.getId(), user, score);

    // then
    assertThat(movieRatingRepository.findByMovieAndUser(movie, user)).isNotNull();
  }

  @DisplayName("[성공] 추가 + 수정")
  @Test
  public void createAndUpdate() {
    // given
    int score = 3;
    movieRatingService.createOrUpdate(movie.getId(), user, score);
    MovieRating beforeMovieRating = movieRatingRepository.findByMovieAndUser(movie, user).get();
    long beforeId = beforeMovieRating.getId();
    assertThat(beforeMovieRating.getScore()).isEqualTo(score);

    // when
    score = 5;
    movieRatingService.createOrUpdate(movie.getId(), user, score);

    // then
    MovieRating afterMovieRating = movieRatingRepository.findByMovieAndUser(movie, user).get();
    assertThat(afterMovieRating.getId()).isEqualTo(beforeId);
    assertThat(afterMovieRating.getScore()).isEqualTo(score);
  }
}
