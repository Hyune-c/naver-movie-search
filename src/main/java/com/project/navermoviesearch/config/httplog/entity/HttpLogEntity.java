package com.project.navermoviesearch.config.httplog.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Builder
@Table(name = "http_log")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class HttpLogEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "basic_data", nullable = false, columnDefinition = "text")
  private String basicData;

  @Column(name = "request_params", nullable = false, columnDefinition = "text")
  private String requestParams;

  @Column(name = "request_body", nullable = false, columnDefinition = "text")
  private String requestBody;

  @Column(name = "response_body", nullable = false, columnDefinition = "text")
  private String responseBody;

  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  public static HttpLogEntity of(String basicData, String requestParams, String requestBody, String responseBody) {
    return HttpLogEntity.builder()
        .basicData(basicData)
        .requestParams(requestParams)
        .requestBody(requestBody)
        .responseBody(responseBody)
        .build();
  }
}
