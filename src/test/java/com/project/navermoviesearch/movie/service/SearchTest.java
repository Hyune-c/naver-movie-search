package com.project.navermoviesearch.movie.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[service] 영화 검색")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Transactional
@ActiveProfiles("test")
@SpringBootTest
class SearchTest {

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieSearchService movieSearchService;

  @DisplayName("[성공] 삭제 안된 영화만")
  @Test
  public void notDeleted() {
    // given
    int count = 5;
    String title = "test movie title ";
    IntStream.range(0, count).forEach(i ->
        this.movieRepository.save(Movie.of(title + i))
    );
    assertThat(movieRepository.findAll().size()).isEqualTo(count);

    // when
    movieRepository.findAll().stream()
        .limit(count / 2)
        .forEach(movie -> {
          movie.delete();
          movieRepository.save(movie);
        });

    // then
    assertThat(movieSearchService.search(title).size()).isLessThan(movieRepository.findAll().size());
    log.info("### search count:{}, all count: {}",
        movieSearchService.search(title).size(), movieRepository.findAll().size());
  }
}
