package com.salesanalytics.autotests.elements;

import com.microsoft.playwright.Locator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableElement {
  private final Locator root;

  public TableElement(Locator root) {
    this.root = root;
  }

  public List<String> headers() {
    return root.locator("thead th").allTextContents();
  }

  public List<Map<String, String>> rows() {
    List<String> headers = headers();
    return root
        .locator("tbody tr")
        .all()
        .stream()
        .map(row -> toMap(headers, row.locator("td").allTextContents()))
        .toList();
  }

  public List<String> column(String header) {
    return rows()
        .stream()
        .map(row -> row.get(header))
        .toList();
  }

  private static Map<String, String> toMap(List<String> headers, List<String> cells) {
    Map<String, String> row = new LinkedHashMap<>();
    for (int i = 0; i < headers.size(); i++) {
      row.put(headers.get(i), cells.get(i));
    }
    return row;
  }
}
