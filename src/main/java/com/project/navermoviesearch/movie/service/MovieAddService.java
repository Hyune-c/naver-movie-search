package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.service.NaverSearchMovieService;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.repository.MovieRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieAddService {

  private final NaverSearchMovieService naverSearchMovieService;
  private final MovieRepository movieRepository;

  /*
    외부 서비스를 통해 영화 제목을 기반으로 검색합니다.
    DB 에 존재하지 않는 영화라면 저장합니다.
   */
  public List<Movie> addMovie(String title) {
    NaverSearchMovieAggregate naverSearchMovieAggregate = naverSearchMovieService.searchMovies(title);
    return naverSearchMovieAggregate.getMovieList().stream()
        .map(Movie::of)
        .filter(movie -> movieRepository.findByTitle(movie.getTitle()).isEmpty())
        .map(movieRepository::save)
        .collect(Collectors.toList());
  }
}
