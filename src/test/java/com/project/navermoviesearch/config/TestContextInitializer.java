package com.project.navermoviesearch.config;

import java.lang.reflect.Field;
import java.util.Map;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    // 공개 repository 이기에 삭제하였습니다.
    // 테스트 케이스를 돌리시려면 Key 정보를 채워주세요.
    setEnv("NAVER_CLIENT_ID", "");
    setEnv("NAVER_CLIENT_SECRET", "");
    setEnv("JDBC_URL", "");
    setEnv("JDBC_USERNAME", "");
    setEnv("JDBC_PASSWORD", "");
  }

  private static void setEnv(String key, String value) {
    try {
      Map<String, String> env = System.getenv();
      Class<?> cl = env.getClass();
      Field field = cl.getDeclaredField("m");
      field.setAccessible(true);
      Map<String, String> writableEnv = (Map<String, String>) field.get(env);
      writableEnv.put(key, value);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to set environment variable", e);
    }
  }
}
