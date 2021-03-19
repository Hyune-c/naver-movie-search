package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.MovieGenre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

  List<MovieGenre> findAllByMovie_id(Long movieId);
}
