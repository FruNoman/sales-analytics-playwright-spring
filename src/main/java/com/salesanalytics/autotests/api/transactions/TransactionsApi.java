package com.salesanalytics.autotests.api.transactions;

import com.microsoft.playwright.APIResponse;
import com.salesanalytics.autotests.api.core.ApiClient;
import com.salesanalytics.autotests.api.transactions.dto.TransactionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionsApi {
  private static final String TRANSACTIONS = "transactions";

  @Autowired
  private ApiClient api;

  public APIResponse getTransactions(String query) {
    return api.get(TRANSACTIONS + "?" + query);
  }

  public TransactionsResponse body(APIResponse response) {
    return api.read(response, TransactionsResponse.class);
  }
}
