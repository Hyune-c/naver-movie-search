package com.project.navermoviesearch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

@Component
public class TestUtil {

  private static ObjectMapper objectMapper;

  private TestUtil(ObjectMapper objectMapper) {
    TestUtil.objectMapper = objectMapper;
  }

  public static <T> List<T> mvcResultToList(MvcResult result, Class<T> valueType) throws Exception {
    return objectMapper.readValue(result.getResponse().getContentAsString(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
  }

  public static <T> List<T> pageMvcResultToList(MvcResult result, Class<T> valueType) throws Exception {
    String contents = JsonPath.parse(result.getResponse().getContentAsString()).read("content").toString();
    return objectMapper.readValue(contents, objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
  }

  public static <T> T mvcResultToObject(MvcResult result, Class<T> valueType) throws Exception {
    return objectMapper.readValue(result.getResponse().getContentAsString(), valueType);
  }
}
