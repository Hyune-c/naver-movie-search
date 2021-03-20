package com.project.navermoviesearch.batch;

import com.project.navermoviesearch.movie.comment.repository.MovieCommentRepository;
import com.project.navermoviesearch.movie.comment.service.MovieCommentDeleteService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Spring Batch 를 구현하기 전 임시로 만든 서비스입니다.
 */
@RequiredArgsConstructor
@Service
public class BatchService {

  private final MovieCommentDeleteService movieCommentDeleteService;

  private final MovieCommentRepository movieCommentRepository;


  /**
   * 작성일이 day 보다 오래된 코멘트를 삭제합니다.
   *
   * @param day
   * @return
   */
  @Async
  public void deleteMovieCommentOverDay(int day) {
    movieCommentRepository.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(day))
        .forEach(movieCommentDeleteService::delete);
  }
}
