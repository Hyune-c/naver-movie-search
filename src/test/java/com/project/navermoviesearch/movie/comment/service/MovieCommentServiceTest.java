package com.project.navermoviesearch.movie.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 영화 코멘트")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Sql({"classpath:database/initUser.sql", "classpath:database/initMovie.sql"})
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class MovieCommentServiceTest {

  @Autowired
  private MovieCommentService movieCommentService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieCommentRepository movieCommentRepository;

  private List<User> userList;
  private List<Movie> movieList;
  User user;
  Movie movie;

  @BeforeEach
  public void beforeAll() {
    userList = userRepository.findAll();
    movieList = movieRepository.findAll();
    user = userList.get(0);
    movie = movieList.get(0);
  }

  @DisplayName("[성공] 삭제")
  @Test
  public void delete() {
    // given
    String contents = "코멘트 테스트123";

    movie.getComments().add(MovieComment.of(movie, user, contents));
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, user).size()).isEqualTo(1);

    // when
    movieCommentService.delete(user, movie.getComments().get(0).getId());

    // then
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, user).size()).isEqualTo(0);
  }

  @DisplayName("[실패] 다른 사람이 삭제")
  @Test
  public void delete_otherUser() {
    // given
    String contents = "코멘트 테스트123";

    movie.getComments().add(MovieComment.of(movie, user, contents));
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, user).size()).isEqualTo(1);

    // when
    User user2 = userList.get(1);
    BusinessException exception = assertThrows(BusinessException.class,
        () -> movieCommentService.delete(user2, movie.getComments().get(0).getId()));

    // then
    assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FORBIDDEN);
  }
}
