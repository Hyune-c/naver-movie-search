package com.project.navermoviesearch.config.httplog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.navermoviesearch.config.httplog.entity.HttpLogEntity;
import com.project.navermoviesearch.config.httplog.repository.HttpLogRepository;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {

  private final ObjectMapper objectMapper;
  private final HttpLogRepository httpLogRepository;

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if (request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper")) {
      return;
    }

    Map<String, String> basicData = createBasicData(request, response);
    final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
    final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

    httpLogRepository.save(HttpLogEntity.of(
        objectMapper.writeValueAsString(basicData),
        objectMapper.writeValueAsString(request.getParameterMap()),
        objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString(),
        objectMapper.readTree(cachingResponse.getContentAsByteArray()).toString()));
  }

  private Map<String, String> createBasicData(HttpServletRequest request, HttpServletResponse response) {
    return Map.of(
        "method", request.getMethod(),
        "requestUri", request.getRequestURI(),
        "remoteHost", request.getRemoteHost(),
        "status", String.valueOf(response.getStatus())
    );
  }
}

