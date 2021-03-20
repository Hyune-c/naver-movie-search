package com.project.navermoviesearch.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class QueryDslUtils {

  public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> function) {
    try {
      return new BooleanBuilder(function.get());
    } catch (NullPointerException | IllegalArgumentException e) {
      return new BooleanBuilder();
    }
  }

  public static <T> Slice<T> toSlice(List<T> content, Pageable pageable) {
    boolean hasNext = false;

    if (content.size() > pageable.getPageSize()) {
      content.remove(content.size() - 1);
      hasNext = true;
    }
    return new SliceImpl<>(content, pageable, hasNext);
  }
}
