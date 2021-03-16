package com.project.navermoviesearch.external.service;

import static com.project.navermoviesearch.config.handler.ErrorCode.EXTERNAL_ILLEGAL_DATA;
import static com.project.navermoviesearch.config.handler.ErrorCode.EXTERNAL_UNKNOWN_FAILED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.code.GenreCode;
import com.project.navermoviesearch.config.external.NaverConfigure.NaverConfig;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.external.NaverSearchMovieAggregate;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesRequest;
import com.project.navermoviesearch.external.dto.NaverSearchMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class NaverSearchMovieService {

  private final NaverConfig naverConfig;
  private final String BASE_URL = "https://openapi.naver.com/v1/search/movie.json";

  private final ObjectMapper objectMapper;

  public NaverSearchMovieAggregate searchMovies(String query, GenreCode genreCode) {
    String responseString = request(NaverSearchMoviesRequest.of(query, genreCode))
        .block();

    try {
      NaverSearchMoviesResponse response = objectMapper.readValue(responseString, NaverSearchMoviesResponse.class);
      return NaverSearchMovieAggregate.of(response, genreCode);
    } catch (JsonProcessingException ex) {
      throw new BusinessException(EXTERNAL_ILLEGAL_DATA);
    } catch (Exception ex) {
      throw new BusinessException(EXTERNAL_UNKNOWN_FAILED);
    }
  }

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
            .queryParam("genre", request.getGenre().getNaverCode())
            .queryParam("country", request.getCountry())
            .queryParam("yearfrom", request.getYearfrom())
            .queryParam("yearto", request.getYearto())
            .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}
