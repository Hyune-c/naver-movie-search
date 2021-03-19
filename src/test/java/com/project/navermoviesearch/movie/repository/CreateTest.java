package com.project.navermoviesearch.movie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.entity.MovieGenre;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("[repo] 영화 추가")
@Transactional
@DataJpaTest
class CreateTest {

  @Autowired
  private MovieRepository movieRepository;

  @Autowired
  private MovieGenreRepository movieGenreRepository;

  @DisplayName("[성공] with 장르")
  @Test
  public void withGenres() {
    // given
    Movie movie = Movie.of("test title");
    movie.getGenres().addAll(
        List.of(MovieGenre.of(GenreCode.ACTION, movie),
            MovieGenre.of(GenreCode.SF, movie),
            MovieGenre.of(GenreCode.DRAMA, movie))
    );

    // when
    this.movieRepository.save(movie);

    // then
    assertThat(movieRepository.findById(movie.getId())).isPresent();
    assertThat(movieRepository.findById(movie.getId()).get().getGenres().size()).isGreaterThan(0);
    assertThat(movieGenreRepository.findAllByMovie_id(movie.getId()).size()).isGreaterThan(0);
  }
}
