package com.project.navermoviesearch.movie.repository;

import com.project.navermoviesearch.movie.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MovieRepositoryCustom {

  Slice<Movie> findAllByTitleContainingAndDeletedIsFalse(Pageable pageable, String title);

//  Slice<? extends GoodsAggregate> findAllAboutBrand(User user, Pageable pageable, MatchingSectorsType sectorsType);
//
//  Slice<? extends GoodsAggregate> findAllAboutBrokerageStore(User user, Pageable pageable, String sido, String sigungu);
//
//  List<? extends GoodsAggregate> findAllById(User user, Goods.Type goodsType, List<Long> ids);
//
//  GoodsAggregate findAggregateById(User user, Long id);
//
//  List<? extends GoodsAggregate> findAllOrderByCreatedAtDescAndLimit(User user, Goods.Type goodsType, Long limit);
//
//  List<? extends GoodsAggregate> findAllOrderByCreatedAtDescAndLimit(User user, MatchingSectorsType sectorsType, Goods.Type goodsType,
//      Long limit);
//
//  /*
//    User 를 기준으로 북마크된 상품을 가져옵니다.
//   */
//  Slice<? extends GoodsAggregate> findAllAboutBookmark(User user, Pageable pageable);
}
