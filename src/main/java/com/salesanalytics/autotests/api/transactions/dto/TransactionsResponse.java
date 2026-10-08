package com.salesanalytics.autotests.api.transactions.dto;

import java.util.List;
import java.util.Map;

public record TransactionsResponse(List<Transaction> data, Meta meta) {
  public record Meta(int total, int returned, Map<String, String> filters) {}
}
