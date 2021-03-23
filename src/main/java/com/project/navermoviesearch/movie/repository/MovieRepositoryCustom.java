package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MovieRepositoryCustom {

  Slice<Movie> findAllByTitleContainingAndDeletedIsFalse(Pageable pageable, String title);
}
