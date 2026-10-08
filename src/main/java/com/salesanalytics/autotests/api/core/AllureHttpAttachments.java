package com.salesanalytics.autotests.api.core;

import com.microsoft.playwright.APIResponse;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;

final class AllureHttpAttachments {
  private AllureHttpAttachments() {}

  static void attach(String method, APIResponse response) {
    DefaultAttachmentProcessor processor = new DefaultAttachmentProcessor();
    processor.addAttachment(
        HttpRequestAttachment.Builder
            .create("Request: " + method + " " + response.url(), response.url())
            .setMethod(method)
            .build(),
        new FreemarkerAttachmentRenderer("http-request.ftl"));
    processor.addAttachment(
        HttpResponseAttachment.Builder
            .create("Response: " + response.status())
            .setUrl(response.url())
            .setResponseCode(response.status())
            .setHeaders(response.headers())
            .setBody(response.text())
            .build(),
        new FreemarkerAttachmentRenderer("http-response.ftl"));
  }
}
