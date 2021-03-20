package com.project.navermoviesearch.movie.comment.repository;

import com.project.navermoviesearch.movie.comment.entity.MovieCommentDeleted;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCommentDeletedRepository extends JpaRepository<MovieCommentDeleted, Long> {

  List<MovieCommentDeleted> findAllByMovieAndUser(Movie movie, User user);
}
