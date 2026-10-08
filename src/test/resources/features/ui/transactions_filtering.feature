@ui
Feature: Filtering and sorting transactions in the UI

  Scenario: Clear removes the filters but keeps the selected sorting
    Given the Sales Analytics page is open
    When the user selects "Europe" in "Region"
    And the user selects "Furniture" in "Category"
    And the user selects "Revenue: High → Low" in "Sort by"
    And the user clicks Apply
    Then the table shows "transactions/europe-furniture-revenue-desc.json"
    When the user clicks Clear
    Then no filter is selected
    And "Sort by" shows "Revenue: High → Low"
    And the table shows "transactions/all-revenue-desc.json"

  Scenario: Filters that match nothing show "No data available"
    Given the Sales Analytics page is open
    When the user selects "Last 7 days" in "Date range"
    And the user selects "Asia" in "Region"
    And the user clicks Apply
    Then the table shows "transactions/last-7-days-asia.json"
    And the table says "No data available"
