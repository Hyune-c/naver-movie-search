package com.project.navermoviesearch.movie.entity;

import com.project.navermoviesearch.code.GenreCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Setter
@Getter
@Builder
@Table(name = "movie_genre")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
public class MovieGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "genre")
  private GenreCode genre;

  @ManyToOne
  @JoinColumn(name = "movie_id")
  private Movie movie;

  public static MovieGenre of(GenreCode genre, Movie movie) {
    return MovieGenre.builder()
        .genre(genre)
        .movie(movie)
        .build();
  }
}
