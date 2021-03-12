package com.project.navermoviesearch.config.external;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NaverConfigure {

  @Bean
  public NaverConfig naverConfig() {
    if (System.getenv("NAVER_CLIENT_ID") == null
        || System.getenv("NAVER_CLIENT_SECRET") == null) {
      throw new IllegalArgumentException("Failed NaverConfigure.");
    }

    return new NaverConfig(System.getenv("NAVER_CLIENT_ID"), System.getenv("NAVER_CLIENT_SECRET"));
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  public class NaverConfig {

    private final String clientId;
    private final String clientSecret;
  }
}
