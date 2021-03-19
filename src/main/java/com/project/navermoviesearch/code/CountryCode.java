package com.project.navermoviesearch.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CountryCode {

  KR("한국", "KR");

  private final String description;
  private final String naverCode;
}
