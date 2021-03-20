package com.project.navermoviesearch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@EnableJpaAuditing
@EnableAspectJAutoProxy
@SpringBootApplication
public class NaverMovieSearchApplication {

  public static void main(String[] args) {
    SpringApplication.run(NaverMovieSearchApplication.class, args);
  }
}
