package com.salesanalytics.autotests.api.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiClient {
  private static final Logger log = LoggerFactory.getLogger(ApiClient.class);

  @Autowired
  private APIRequestContext request;

  @Autowired
  private ObjectMapper objectMapper;

  public APIResponse get(String route) {
    APIResponse response = request.get(route);
    log.info("GET {} -> {}", response.url(), response.status());
    AllureHttpAttachments.attach("GET", response);
    return response;
  }

  public <T> T read(APIResponse response, Class<T> type) {
    try {
      return objectMapper.readValue(response.text(), type);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(
          response.url() + ": the body is not a " + type.getSimpleName() + "\n" + response.text(),
          e);
    }
  }
}
