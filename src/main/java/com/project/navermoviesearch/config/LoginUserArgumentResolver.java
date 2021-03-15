package com.project.navermoviesearch.config;

import com.project.navermoviesearch.config.annotation.LoginUser;
import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.user.service.session.UserSessionService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private static final String AUTHORIZATION_KEY = "Authorization";

  private final UserSessionService userSessionService;

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    LoginUser loginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class);

    String authorizationValue = request.getHeader(AUTHORIZATION_KEY);

    // 1. authorization 이 존재하면 세션을 확인 후 반환합니다.
    if (!authorizationValue.isEmpty()) {
      try {
        return userSessionService.findByAuthorization(authorizationValue);
      } catch (Exception ex) {
        throw new BusinessException(ErrorCode.FORBIDDEN);
      }
    }

    // 2. 로그인이 필수 값인데 authorization 이 없었으면 에러입니다.
    if (loginUserAnnotation.required()) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    return null;
  }
}
