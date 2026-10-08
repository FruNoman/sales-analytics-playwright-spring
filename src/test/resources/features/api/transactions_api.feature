@api
Feature: GET /api/transactions
  The endpoint behind the table. The dataset is static; the server's "today" is 2025-03-25.

  Scenario: Combined filters return only the transactions matching all of them, in the requested order
    When transactions are requested with "dateRange=30&category=Lighting&sort=rev_desc"
    Then the response status is 200
    And the response body is "transactions/lighting-last-30-days.json"
