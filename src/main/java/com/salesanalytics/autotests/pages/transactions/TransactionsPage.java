package com.salesanalytics.autotests.pages.transactions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.salesanalytics.autotests.api.transactions.dto.Transaction;
import com.salesanalytics.autotests.elements.FindBy;
import com.salesanalytics.autotests.elements.SelectElement;
import com.salesanalytics.autotests.elements.TableElement;
import com.salesanalytics.autotests.pages.common.BasePage;
import io.cucumber.spring.ScenarioScope;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class TransactionsPage extends BasePage {
  @FindBy(label = "Date range")
  private SelectElement dateRange;

  @FindBy(label = "Region")
  private SelectElement region;

  @FindBy(label = "Category")
  private SelectElement category;

  @FindBy(label = "Sort by")
  private SelectElement sortBy;

  @FindBy(role = AriaRole.BUTTON, name = "Apply")
  private Locator applyButton;

  @FindBy(role = AriaRole.BUTTON, name = "Clear")
  private Locator clearButton;

  @FindBy(role = AriaRole.TABLE, name = "Sales transactions")
  private TableElement table;

  @FindBy(css = "#status")
  private Locator loadingStatus;

  @FindBy(css = "#count")
  private Locator footerCount;

  @FindBy(css = "#empty")
  private Locator noDataMessage;

  public TransactionsPage open() {
    page.navigate("/");
    return waitForTable();
  }

  public TransactionsPage select(String filter, String option) {
    filter(filter).select(option);
    return this;
  }

  public TransactionsPage apply() {
    applyButton.click();
    return waitForTable();
  }

  public TransactionsPage clear() {
    clearButton.click();
    return waitForTable();
  }

  public String selectedOption(String filter) {
    return filter(filter).selectedOption();
  }

  public List<Transaction> transactions() {
    return table
        .rows()
        .stream()
        .map(
            row ->
                new Transaction(
                    row.get("Order ID"),
                    row.get("Date"),
                    row.get("Region"),
                    row.get("Category"),
                    Integer.parseInt(row.get("Revenue (USD)").replace(",", ""))))
        .toList();
  }

  public String footerCount() {
    return footerCount.textContent();
  }

  public String noDataMessage() {
    return noDataMessage.isVisible() ? noDataMessage.textContent() : "";
  }

  private TransactionsPage waitForTable() {
    assertThat(loadingStatus).isEmpty();
    return this;
  }

  private SelectElement filter(String label) {
    return switch (label) {
      case "Date range" -> dateRange;
      case "Region" -> region;
      case "Category" -> category;
      case "Sort by" -> sortBy;
      default -> throw new IllegalArgumentException("No filter labelled '" + label + "'");
    };
  }
}
