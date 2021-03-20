package com.project.navermoviesearch.movie.repository;

import static com.project.navermoviesearch.movie.entity.QMovie.movie;
import static com.project.navermoviesearch.movie.rating.entity.QMovieRating.movieRating;

import com.project.navermoviesearch.code.SortKey;
import com.project.navermoviesearch.movie.MovieDto;
import com.project.navermoviesearch.movie.entity.Movie;
import com.project.navermoviesearch.util.QueryDslUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class MovieRepositoryImpl implements MovieRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public Slice<Movie> findAllByTitleContainingAndDeletedIsFalse(Pageable pageable, String title) {
    JPAQuery<Movie> jpaQuery =
        queryFactory.selectFrom(movie)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());

    Sort sort = pageable.getSort();
    sort.get().forEach(order -> {
          if (order.getProperty().equals(SortKey.Movie.RATING.name())) {
            jpaQuery.leftJoin(movieRating).on(movie.id.eq(movieRating.movie.id))
                .groupBy(movie)
                .orderBy(movieRating.score.avg().desc());
          }
        }
    );

    List<Movie> content = jpaQuery.fetch();

    return new SliceImpl<>(content, pageable, QueryDslUtils.toSlice(content, pageable).hasNext());
  }

  private QBean<MovieDto> movieDtoQBean() {
    return Projections.bean(
        MovieDto.class,
        movie,
        movie.id.as("averageRating"));
  }
}
