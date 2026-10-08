package com.salesanalytics.autotests.config;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.salesanalytics.autotests.scope.PlaywrightScope;
import io.cucumber.spring.ScenarioScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class PlaywrightConfig {
  @Bean
  @Scope(PlaywrightScope.NAME)
  public Playwright playwright() {
    return Playwright.create();
  }

  @Bean
  @Scope(PlaywrightScope.NAME)
  public Browser browser(Playwright playwright, BrowserProperties browser) {
    BrowserType type =
        switch (browser.name()) {
          case "chromium" -> playwright.chromium();
          case "firefox" -> playwright.firefox();
          case "webkit" -> playwright.webkit();
          default -> throw new IllegalArgumentException("Unknown browser.name: " + browser.name());
        };
    return type.launch(
        new BrowserType.LaunchOptions()
            .setHeadless(browser.headless())
            .setSlowMo(browser.slowMo()));
  }

  @Bean
  @ScenarioScope(proxyMode = ScopedProxyMode.INTERFACES)
  public BrowserContext browserContext(
      Browser browser, BrowserProperties properties, @Value("${app.url}") String appUrl) {
    BrowserContext context =
        browser.newContext(
            new Browser.NewContextOptions()
                .setBaseURL(appUrl)
                .setViewportSize(1440, 900));
    context.setDefaultTimeout(properties.timeout().toMillis());
    context.tracing().start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
    return context;
  }

  @Bean(destroyMethod = "")
  @ScenarioScope(proxyMode = ScopedProxyMode.INTERFACES)
  public Page page(BrowserContext browserContext) {
    return browserContext.newPage();
  }

  @Bean(destroyMethod = "dispose")
  @ScenarioScope(proxyMode = ScopedProxyMode.INTERFACES)
  public APIRequestContext apiRequestContext(
      Playwright playwright, BrowserProperties properties, @Value("${api.url}") String apiUrl) {
    return playwright
        .request()
        .newContext(
            new APIRequest.NewContextOptions()
                .setBaseURL(apiUrl)
                .setTimeout(properties.timeout().toMillis()));
  }
}
