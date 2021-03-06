package com.project.navermoviesearch.movie.rating.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.movie.rating.repository.MovieRatingRepository;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 영화 평점")
@Sql({"classpath:database/initUser.sql", "classpath:database/initMovie.sql"})
@Transactional
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

  private List<User> userList;
  private List<Movie> movieList;
  User user;
  Movie movie;

  @BeforeEach
  public void beforeEach() {
    userList = userRepository.findAll();
    movieList = movieRepository.findAll();
    user = userList.get(0);
    movie = movieList.get(0);
  }

  @DisplayName("[성공] 추가")
  @Test
  public void create() {
    // given
    int score = 4;

    // when
    movieRatingService.createOrUpdate(movie, user, score);

    // then
    assertThat(movieRatingRepository.findByMovieAndUser(movie, user)).isNotNull();
  }

  @DisplayName("[성공] 추가 + 수정")
  @Test
  public void createAndUpdate() {
    // given
    int score = 3;
    movieRatingService.createOrUpdate(movie, user, score);
    MovieRating beforeMovieRating = movieRatingRepository.findByMovieAndUser(movie, user).get();
    long beforeId = beforeMovieRating.getId();
    assertThat(beforeMovieRating.getScore()).isEqualTo(score);

    // when
    score = 5;
    movieRatingService.createOrUpdate(movie, user, score);

    // then
    MovieRating afterMovieRating = movieRatingRepository.findByMovieAndUser(movie, user).get();
    assertThat(afterMovieRating.getId()).isEqualTo(beforeId);
    assertThat(afterMovieRating.getScore()).isEqualTo(score);
  }
}
