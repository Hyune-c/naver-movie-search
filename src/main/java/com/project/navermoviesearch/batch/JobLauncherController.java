package com.project.navermoviesearch.batch;

import com.project.navermoviesearch.config.annotation.LoginUser;
import com.project.navermoviesearch.user.entity.UserSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "배치")
@RequiredArgsConstructor
@RestController
public class JobLauncherController {

  private final BatchService batchService;

  private final String ADMIN_LOGINID = "dev1@test.com";
  private final Integer DAY = 2;


  @Operation(summary = "작성일로부터 2일이 지난 코멘트 삭제", description = "Spring Batch 와 Quartz 를 사용해서 스케줄러를 만들어야 하지만 이번 구현에서는 임시로 만들었습니다.")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @GetMapping("/batch")
  public void batch(@LoginUser UserSession session) {

    if (session.getUser().getLoginId().equals(ADMIN_LOGINID)) {
      batchService.deleteMovieCommentOverDay(DAY);
    }
  }
}
