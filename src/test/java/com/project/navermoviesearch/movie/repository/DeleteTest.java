package com.project.navermoviesearch.movie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.config.TestContextInitializer;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.entity.MovieGenre;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[repository] 영화 삭제")
@ContextConfiguration(initializers = TestContextInitializer.class)
@Transactional
@ActiveProfiles("test")
@DataJpaTest
class DeleteTest {

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieGenreRepository movieGenreRepository;

  @DisplayName("[성공] 삭제")
  @Test
  public void withGenres() {
    // given
    Movie movie = Movie.of("parent 45");
    movie.getGenres().addAll(
        List.of(MovieGenre.of(GenreCode.ACTION, movie),
            MovieGenre.of(GenreCode.SF, movie),
            MovieGenre.of(GenreCode.DRAMA, movie))
    );
    movieRepository.save(movie);
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieRepository.findById(movie.getId()).get().getGenres().size()).isGreaterThan(0);
    assertThat(movieGenreRepository.findAllByMovie_id(movie.getId()).size()).isGreaterThan(0);

    // when
    movieRepository.delete(movie);

    // then
    assertThat(movieRepository.findById(movie.getId())).isEmpty();
    assertThat(movieGenreRepository.findAllByMovie_id(movie.getId())).isEmpty();
  }

  @DisplayName("[성공] 삭제 - 장르만")
  @Test
  public void onlyGenres() {
    // given
    Movie movie = Movie.of("parent 45");
    movie.getGenres().addAll(
        List.of(MovieGenre.of(GenreCode.ACTION, movie),
            MovieGenre.of(GenreCode.SF, movie),
            MovieGenre.of(GenreCode.DRAMA, movie))
    );
    movieRepository.save(movie);
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieRepository.findById(movie.getId()).get().getGenres().size()).isGreaterThan(0);
    int beforeSize = movieGenreRepository.findAllByMovie_id(movie.getId()).size();

    // when
    movie.getGenres().remove(0);

    // then
    int afterSize = movieGenreRepository.findAllByMovie_id(movie.getId()).size();
    assertThat(beforeSize).isEqualTo(afterSize + 1);
  }
}
