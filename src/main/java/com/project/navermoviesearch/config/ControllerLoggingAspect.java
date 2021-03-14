package com.project.navermoviesearch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class ControllerLoggingAspect {

  private final ObjectMapper objectMapper;

  private List<String> args;
  private List<String> failedParsingArgs;


  @Pointcut("within(com.project.navermoviesearch.controller..*)")
  public void onRequest() {
  }

  @Around("com.project.navermoviesearch.config.ControllerLoggingAspect.onRequest()")
  public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    HttpServletResponse response =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

    long start = System.currentTimeMillis();

    String requestParams = objectMapper.writeValueAsString(request.getParameterMap());
    parseArgs(pjp);

    try {
      return pjp.proceed(pjp.getArgs());
    } finally {
      long end = System.currentTimeMillis();
      log.info("### Request: {} {} < {}, {} ({}ms)",
          request.getMethod(), request.getRequestURI(), request.getRemoteHost(), response.getStatus(), end - start);
      log.info("### requestParams: {}", requestParams);
      log.info("### args: {}", objectMapper.writeValueAsString(args));
      log.info("### Failed parsing args: {}", objectMapper.writeValueAsString(failedParsingArgs));
    }
  }

  private void parseArgs(ProceedingJoinPoint pjp) {
    args = new LinkedList<>();
    failedParsingArgs = new LinkedList<>();

    for (Object arg : pjp.getArgs()) {
      try {
        args.add(objectMapper.writeValueAsString(arg));
      } catch (Exception ex) {
        failedParsingArgs.add(arg.getClass().getName());
      }
    }
  }
}
