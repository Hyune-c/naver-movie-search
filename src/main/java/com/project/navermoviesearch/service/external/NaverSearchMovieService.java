package com.project.navermoviesearch.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.external.NaverConfigure.NaverConfig;
import com.project.navermoviesearch.service.external.dto.SearchMoviesRequestDto;
import com.project.navermoviesearch.service.external.dto.SearchMoviesResponseDto;
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

  public SearchMoviesResponseDto searchMovies(SearchMoviesRequestDto dto) {
    String result = request(dto)
        .block();

    try {
      return objectMapper.readValue(result, SearchMoviesResponseDto.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("응답 객체 생성 실패");
    }
  }

  private Mono<String> request(SearchMoviesRequestDto dto) {
    return WebClient.builder()
        .baseUrl(BASE_URL)
        .defaultHeaders(headers -> {
          headers.add("X-Naver-Client-Id", naverConfig.getClientId());
          headers.add("X-Naver-Client-Secret", naverConfig.getClientSecret());
        })
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("query", dto.getQuery())
            .queryParam("display", dto.getDisplay())
            .queryParam("start", dto.getStart())
            .queryParam("genre", dto.getGenre())
            .queryParam("country", dto.getCountry())
            .queryParam("yearfrom", dto.getYearfrom())
            .queryParam("yearto", dto.getYearto())
            .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}
