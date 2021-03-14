package com.project.navermoviesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NaverMovieSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(NaverMovieSearchApplication.class, args);
  }
}
