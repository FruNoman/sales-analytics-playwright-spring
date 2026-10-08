package com.salesanalytics.autotests.steps.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.microsoft.playwright.APIResponse;
import com.salesanalytics.autotests.api.transactions.TransactionsApi;
import com.salesanalytics.autotests.api.transactions.dto.TransactionsResponse;
import com.salesanalytics.autotests.data.TestDataLoader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionsApiSteps {
  @Autowired
  private TransactionsApi transactionsApi;

  @Autowired
  private TestDataLoader testData;

  private APIResponse response;

  @When("transactions are requested with {string}")
  public void transactionsAreRequestedWith(String query) {
    response = transactionsApi.getTransactions(query);
  }

  @Then("the response status is {int}")
  public void theResponseStatusIs(int status) {
    assertThat(response.status()).as("status of " + response.url()).isEqualTo(status);
  }

  @Then("the response body is {string}")
  public void theResponseBodyIs(String expectedFile) {
    TransactionsResponse expected = testData.load(expectedFile, TransactionsResponse.class);

    assertThat(transactionsApi.body(response)).usingRecursiveComparison().isEqualTo(expected);
  }
}
