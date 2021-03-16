package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.external.service.NaverSearchMovieService;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.entity.MovieGenre;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddMovieService {

  private final NaverSearchMovieService naverSearchMovieService;
  private final MovieRepository movieRepository;

  public Movie addMovie(String title) {
    Movie movie = movieRepository.findByTitle(title).orElseGet(() -> Movie.of(title));
    Arrays.stream(GenreCode.values()).forEach(genreCode -> {
      if (naverSearchMovieService.searchMovies(title, genreCode).hasMovie()) {
        movie.getGenres().add(MovieGenre.of(genreCode, movie));
      }

      try {
        Thread.sleep(2000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    return movieRepository.save(movie);
  }
}
