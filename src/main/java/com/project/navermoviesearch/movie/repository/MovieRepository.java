package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findByTitle(String title);
}
