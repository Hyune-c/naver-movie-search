package com.project.navermoviesearch.movie.comment.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import com.project.navermoviesearch.user.entity.User;
import com.project.navermoviesearch.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[repository] 영화 코멘트")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Transactional
@ActiveProfiles("test")
@DataJpaTest
class MovieCommentRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieCommentRepository movieCommentRepository;

  private User testUser;
  private User testUser2;

  @BeforeEach
  public void beforeEach() {
    testUser = User.of("test@test.com", "choiroot");
    testUser.setCreatedAt(LocalDateTime.now());
    testUser.setUpdatedAt(LocalDateTime.now());
    userRepository.save(testUser);

    testUser2 = User.of("test2@test.com", "choiroot2");
    testUser2.setCreatedAt(LocalDateTime.now());
    testUser2.setUpdatedAt(LocalDateTime.now());
    userRepository.save(testUser2);
  }

  @AfterEach
  public void afterEach() {
    userRepository.deleteAll();
  }

  @DisplayName("[성공] 추가")
  @Test
  public void success() {
    // given
    Movie movie = Movie.of("test movie title");
    List<String> contentList = List.of("코멘트1 테스트 입니다.", "코멘트2 테스트 입니다.");

    // when
    contentList.forEach(content -> {
      movie.getComments().add(MovieComment.of(movie, testUser, content));
      this.movieRepository.save(movie);
    });

    // then
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, testUser).size()).isEqualTo(2);
  }

  @DisplayName("[성공] 추가 - 서로 다른 회원")
  @Test
  public void differentUser() {
    // given
    Movie movie = Movie.of("test movie title");
    List<String> contentList1 = List.of("회원1 코멘트1 테스트 입니다.", "회원1 코멘트2 테스트 입니다.");
    List<String> contentList2 = List.of("회원2 코멘트1 테스트 입니다.", "회원2 코멘트2 테스트 입니다.", "회원2 코멘트3 테스트 입니다.");

    // when
    contentList1.forEach(content -> {
      movie.getComments().add(MovieComment.of(movie, testUser, content));
      this.movieRepository.save(movie);
    });

    contentList2.forEach(content -> {
      movie.getComments().add(MovieComment.of(movie, testUser2, content));
      this.movieRepository.save(movie);
    });

    // then
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, testUser).size()).isEqualTo(contentList1.size());
    assertThat(movieCommentRepository.findAllByMovieAndUserAndDeletedIsFalse(movie, testUser2).size()).isEqualTo(contentList2.size());
    assertThat(movieCommentRepository.findAll().size()).isEqualTo(contentList1.size() + contentList2.size());
  }
}
