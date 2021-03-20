package com.project.navermoviesearch.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class SortKey {

  @Getter
  @RequiredArgsConstructor
  public enum Movie {

    RATING("평점");

    private final String description;
  }
}
