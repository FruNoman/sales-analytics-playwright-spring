package com.salesanalytics.autotests.pages.common;

import com.microsoft.playwright.Page;
import com.salesanalytics.autotests.elements.FindBy;
import com.salesanalytics.autotests.elements.PageFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasePage {
  @Autowired
  protected Page page;

  @PostConstruct
  private void initElements() {
    PageFactory.initElements(this, page);
  }
}
