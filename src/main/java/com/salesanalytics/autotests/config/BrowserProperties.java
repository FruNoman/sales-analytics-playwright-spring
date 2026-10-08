package com.salesanalytics.autotests.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "browser")
public record BrowserProperties(String name, boolean headless, double slowMo, Duration timeout) {}
