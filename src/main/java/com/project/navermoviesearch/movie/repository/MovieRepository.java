package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findByTitle(String title);

  List<Movie> findAllByTitleContainingAndDeletedIsFalse(String title);

  Slice<Movie> findAllByTitleContainingAndDeletedIsFalse(Pageable pageable, String title);
}
