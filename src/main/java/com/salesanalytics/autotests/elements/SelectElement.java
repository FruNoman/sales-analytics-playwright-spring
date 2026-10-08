package com.salesanalytics.autotests.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;

public class SelectElement {
  private final Locator root;

  public SelectElement(Locator root) {
    this.root = root;
  }

  public void select(String option) {
    root.selectOption(new SelectOption().setLabel(option));
  }

  public String selectedOption() {
    return root.locator("option:checked").textContent();
  }
}
