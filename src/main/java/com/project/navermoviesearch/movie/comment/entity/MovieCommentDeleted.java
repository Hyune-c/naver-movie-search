package com.project.navermoviesearch.movie.comment.entity;

import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Builder
@Table(name = "movie_comment_deleted")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class MovieCommentDeleted {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "content")
  private String content;

  @CreatedDate
  @Column(name = "deleted_at", nullable = false)
  private LocalDateTime deletedAt;

  public static MovieCommentDeleted of(MovieComment movieComment) {
    return MovieCommentDeleted.builder()
        .user(movieComment.getUser())
        .movie(movieComment.getMovie())
        .content(movieComment.getContent())
        .build();
  }
}
