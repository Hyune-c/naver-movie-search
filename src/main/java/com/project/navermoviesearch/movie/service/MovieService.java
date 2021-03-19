package com.project.navermoviesearch.movie.service;

import com.project.navermoviesearch.movie.MovieDto;
import com.project.navermoviesearch.movie.comment.service.MovieCommentCreateService;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.service.MovieRatingService;
import com.project.navermoviesearch.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

  private final MovieAddService movieAddService;
  private final MovieFindService movieFindService;
  private final MovieSearchService movieSearchService;

  private final MovieCommentCreateService movieCommentCreateService;

  private final MovieRatingService movieRatingService;

  public void addMovie(String title) {
    movieAddService.addMovie(title);
  }

  public List<MovieDto> search(String title) {
    return movieSearchService.search(title).stream()
        .map(MovieDto::of)
        .collect(Collectors.toList());
  }

  public Slice<MovieDto> search(Pageable pageable, String title) {
    return movieSearchService.search(pageable, title)
        .map(movie -> {
          double averageRating = movieRatingService.calcAverageRating(movie);
          return MovieDto.of(movie, averageRating);
        });
  }

  public long create(long movieId, User user, String content) {
    return movieCommentCreateService.createComment(
        movieFindService.findById(movieId), user, content).getId();
  }

  public Movie findByMovieId(long movieId) {
    return movieFindService.findById(movieId);
  }
}
