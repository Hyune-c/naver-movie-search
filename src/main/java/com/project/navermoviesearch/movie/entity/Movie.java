package com.project.navermoviesearch.movie.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Builder
@Table(name = "movie")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "link_url")
  private String linkUrl;

  @Column(name = "image_url")
  private String imageUrl;

  @Column(name = "sub_title")
  private String subTitle;

  @Column(name = "publish_year")
  private String publishYear;

  @Column(name = "directors")
  private String directors;

  @Column(name = "actors")
  private String actors;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MovieGenre> genres = new ArrayList<>();

  public Movie(String title) {
    this.title = title;
  }
}
