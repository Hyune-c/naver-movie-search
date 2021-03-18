package com.project.navermoviesearch.external.service;

import static com.project.navermoviesearch.config.handler.ErrorCode.EXTERNAL_ILLEGAL_DATA;
import static com.project.navermoviesearch.config.handler.ErrorCode.EXTERNAL_UNKNOWN_FAILED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.external.NaverConfigure.NaverConfig;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesRequest;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverSearchMovieService {

  private final NaverConfig naverConfig;
  private final String BASE_URL = "https://openapi.naver.com/v1/search/movie.json";

  private final ObjectMapper objectMapper;

  public NaverSearchMovieAggregate searchMovies(String query) {
    String responseString = request(NaverSearchMoviesRequest.of(query))
        .block();

    try {
      NaverSearchMoviesResponse response = objectMapper.readValue(responseString, NaverSearchMoviesResponse.class);
      return NaverSearchMovieAggregate.of(response);
    } catch (JsonProcessingException ex) {
      throw new BusinessException(EXTERNAL_ILLEGAL_DATA);
    } catch (Exception ex) {
      throw new BusinessException(EXTERNAL_UNKNOWN_FAILED);
    }
  }

  /*
    - 네이버 API 의 기술적인 문제로 Genre 는 사용하지 않습니다.
    - 대신 추가 목표로 모든 연도를 대상으로 합니다.
   */
  private Mono<String> request(NaverSearchMoviesRequest request) {
    return WebClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeaders(headers -> {
          headers.add("X-Naver-Client-Id", naverConfig.getClientId());
          headers.add("X-Naver-Client-Secret", naverConfig.getClientSecret());
        })
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("query", request.getQuery())
            .queryParam("display", request.getDisplay())
            .queryParam("start", request.getStart())
            .queryParam("country", request.getCountry())
            .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}
