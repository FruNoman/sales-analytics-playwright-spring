package com.salesanalytics.autotests.steps.ui;

import static org.assertj.core.api.Assertions.assertThat;

import com.salesanalytics.autotests.api.transactions.dto.TransactionsResponse;
import com.salesanalytics.autotests.data.TestDataLoader;
import com.salesanalytics.autotests.pages.transactions.TransactionsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionsPageSteps {
  private static final List<String> FILTERS = List.of("Date range", "Region", "Category");

  @Autowired
  private TransactionsPage transactionsPage;

  @Autowired
  private TestDataLoader testData;

  @Given("the Sales Analytics page is open")
  public void theSalesAnalyticsPageIsOpen() {
    transactionsPage.open();
  }

  @When("the user selects {string} in {string}")
  public void theUserSelects(String option, String filter) {
    transactionsPage.select(filter, option);
  }

  @When("the user clicks Apply")
  public void theUserClicksApply() {
    transactionsPage.apply();
  }

  @When("the user clicks Clear")
  public void theUserClicksClear() {
    transactionsPage.clear();
  }

  @Then("no filter is selected")
  public void noFilterIsSelected() {
    FILTERS.forEach(
        filter ->
            assertThat(transactionsPage.selectedOption(filter)).as(filter).isEqualTo("Not selected"));
  }

  @Then("{string} shows {string}")
  public void filterShows(String filter, String option) {
    assertThat(transactionsPage.selectedOption(filter)).as(filter).isEqualTo(option);
  }

  @Then("the table says {string}")
  public void theTableSays(String message) {
    assertThat(transactionsPage.noDataMessage()).isEqualTo(message);
  }

  @Then("the table shows {string}")
  public void theTableShows(String expectedFile) {
    TransactionsResponse expected = testData.load(expectedFile, TransactionsResponse.class);

    assertThat(transactionsPage.transactions()).containsExactlyElementsOf(expected.data());

    assertThat(transactionsPage.footerCount())
        .isEqualTo(
            "Showing %d of %d transactions"
                .formatted(expected.meta().returned(), expected.meta().total()));
  }
}
