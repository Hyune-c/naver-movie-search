package com.project.navermoviesearch.config;

import com.project.navermoviesearch.config.annotation.LoginUser;
import com.project.navermoviesearch.config.handler.ErrorCode;
import com.project.navermoviesearch.config.handler.exception.BusinessException;
import com.project.navermoviesearch.user.service.session.UserSessionService;
import java.util.Optional;
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

    String authorizationValue = Optional.ofNullable(request.getHeader(AUTHORIZATION_KEY)).orElse("");

    // 로그인이 필수인데 session 에 존재하지 않으면 에러입니다.
    if (loginUserAnnotation.required()) {
      try {
        return userSessionService.findByAuthorization(authorizationValue);
      } catch (Exception ex) {
        throw new BusinessException(ErrorCode.FORBIDDEN);
      }
    }

    return null;
  }
}
