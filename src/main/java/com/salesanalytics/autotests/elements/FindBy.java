package com.salesanalytics.autotests.elements;

import com.microsoft.playwright.options.AriaRole;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindBy {
  String label() default "";

  AriaRole[] role() default {};

  String name() default "";

  String css() default "";
}
