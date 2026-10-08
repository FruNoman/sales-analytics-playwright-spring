package com.salesanalytics.autotests.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader {
  @Autowired
  private ObjectMapper objectMapper;

  public <T> T load(String path, Class<T> type) {
    try (InputStream json = new ClassPathResource("data/" + path).getInputStream()) {
      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot read data/" + path + " as " + type.getSimpleName(), e);
    }
  }
}
