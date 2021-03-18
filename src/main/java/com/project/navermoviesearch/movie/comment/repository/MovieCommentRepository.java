package com.project.navermoviesearch.movie.comment.repository;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {

  List<MovieComment> findAllByMovieAndUserAndDeletedIsFalse(Movie movie, User user);

  Slice<MovieComment> findAllByMovieAndDeletedIsFalse(Pageable pageable, Movie movie);

  Page<MovieComment> findAllByUserAndDeletedIsFalse(Pageable pageable, User user);
}
