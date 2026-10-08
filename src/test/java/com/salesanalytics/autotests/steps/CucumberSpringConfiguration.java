package com.salesanalytics.autotests.steps;

import com.salesanalytics.autotests.AutotestsApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = AutotestsApplication.class)
public class CucumberSpringConfiguration {}
