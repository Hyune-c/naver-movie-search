package com.project.navermoviesearch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if (request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper")) {
      return;
    }

    Map<String, String> basicData = createBasicData(request, response);
    final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
    final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

    log.info("### basicParams: {}", objectMapper.writeValueAsString(basicData));
    log.info("### request Body : {}", objectMapper.readTree(cachingRequest.getContentAsByteArray()));
    log.info("### response Body : {}", objectMapper.readTree(cachingResponse.getContentAsByteArray()));
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

