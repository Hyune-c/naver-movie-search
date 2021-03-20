package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {

  Optional<Movie> findByTitle(String title);

  List<Movie> findAllByTitleContainingAndDeletedIsFalse(String title);

  Optional<Movie> findByIdAndDeletedIsFalse(Long aLong);
}
