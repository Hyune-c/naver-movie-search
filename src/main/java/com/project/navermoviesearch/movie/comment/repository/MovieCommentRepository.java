package com.project.navermoviesearch.movie.comment.repository;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCommentRepository extends JpaRepository<MovieComment, Long> {

  List<MovieComment> findAllByMovieAndUser(Movie movie, User user);

  Slice<MovieComment> findAllByMovie(Pageable pageable, Movie movie);

  Page<MovieComment> findAllByUser(Pageable pageable, User user);

  List<MovieComment> findAllByCreatedAtBefore(LocalDateTime beforeDateTime);
}
