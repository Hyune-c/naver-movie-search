package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
