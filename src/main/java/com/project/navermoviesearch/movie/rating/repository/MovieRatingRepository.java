package com.project.navermoviesearch.movie.rating.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.movie.rating.entity.MovieRating;
import com.project.navermoviesearch.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

  Optional<MovieRating> findByMovieAndUser(Movie movie, User user);

  List<MovieRating> findAllByMovie(Movie movie);
}
