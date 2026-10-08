package com.salesanalytics.autotests.steps;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks {
  @Autowired
  private BrowserContext browserContext;

  @Autowired
  private Page page;

  @After("@ui")
  public void saveFailureArtifacts(Scenario scenario) throws IOException {
    if (!scenario.isFailed()) {
      return;
    }
    scenario.attach(
        page.screenshot(new Page.ScreenshotOptions().setFullPage(true)), "image/png", "screenshot");
    Path trace = Path.of("target", "traces", scenario.getName().replaceAll("\\W+", "_") + ".zip");
    browserContext.tracing().stop(new Tracing.StopOptions().setPath(trace));
    scenario.attach(Files.readAllBytes(trace), "application/zip", "trace.zip");
    scenario.log("Trace: " + trace.toAbsolutePath() + " (open at https://trace.playwright.dev)");
  }
}
