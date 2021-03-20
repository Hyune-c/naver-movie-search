package com.project.navermoviesearch.batch;

import com.project.navermoviesearch.movie.comment.entity.MovieComment;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPagingItemReaderJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  private int chunkSize = 100;

  @Bean
  public Job jpaPagingItemReaderJob() {
    log.info("### jpaPagingItemReaderJob");
    return jobBuilderFactory.get("jpaPagingItemReaderJob")
        .start(jpaPagingItemReaderStep())
        .build();
  }

  @Bean
  public Step jpaPagingItemReaderStep() {
    log.info("### jpaPagingItemReaderStep");
    return stepBuilderFactory
        .get("jpaPagingItemReaderStep")
        .<MovieComment, MovieComment>chunk(chunkSize)
        .reader(jpaPagingItemReader())
        .writer(jpaPagingItemWriter())
        .build();
  }

  @Bean
  @StepScope
  public JpaPagingItemReader<MovieComment> jpaPagingItemReader() {
    log.info("### jpaPagingItemReader");
    return new JpaPagingItemReaderBuilder<MovieComment>()
        .name("jpaPagingItemReader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(chunkSize)
        .queryString("SELECT id, movie_id, user_id, content, created_at, updated_at  from movie_comment")
        .build();
  }

  private ItemWriter<MovieComment> jpaPagingItemWriter() {
    log.info("### jpaPagingItemWriter");
    return list -> {
      for (MovieComment movieComment : list) {
        log.info("### getContent: {}", movieComment.getContent());
      }
    };
  }
}
