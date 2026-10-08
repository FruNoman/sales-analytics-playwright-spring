package com.salesanalytics.autotests.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.lang.reflect.Field;
import org.springframework.util.ReflectionUtils;

public final class PageFactory {
  private PageFactory() {}

  public static void initElements(Object pageObject, Page page) {
    ReflectionUtils.doWithFields(
        pageObject.getClass(),
        field -> {
          Locator locator = locate(field.getAnnotation(FindBy.class), page);
          ReflectionUtils.makeAccessible(field);
          field.set(pageObject, wrap(field, locator));
        },
        field -> field.isAnnotationPresent(FindBy.class));
  }

  private static Locator locate(FindBy findBy, Page page) {
    if (!findBy.label().isEmpty()) {
      return page.getByLabel(findBy.label());
    }
    if (findBy.role().length > 0) {
      return page.getByRole(findBy.role()[0], new Page.GetByRoleOptions().setName(findBy.name()));
    }
    return page.locator(findBy.css());
  }

  private static Object wrap(Field field, Locator locator) {
    if (field.getType() == Locator.class) {
      return locator;
    }
    try {
      return field.getType().getConstructor(Locator.class).newInstance(locator);
    } catch (ReflectiveOperationException e) {
      throw new IllegalStateException(
          field + ": @FindBy needs a Locator or a type with a (Locator) constructor", e);
    }
  }
}
