package com.salesanalytics.autotests.scope;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.stereotype.Component;

@Component
public class PlaywrightScope implements Scope, BeanFactoryPostProcessor, DisposableBean {
  public static final String NAME = "playwright";

  private final ThreadLocal<Map<String, Object>> beans = ThreadLocal.withInitial(HashMap::new);
  private final Deque<Runnable> destructionCallbacks = new ConcurrentLinkedDeque<>();

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    beanFactory.registerScope(NAME, this);
  }

  @Override
  public Object get(String name, ObjectFactory<?> objectFactory) {
    Map<String, Object> threadBeans = beans.get();
    Object bean = threadBeans.get(name);
    if (bean == null) {
      bean = objectFactory.getObject();
      threadBeans.put(name, bean);
    }
    return bean;
  }

  @Override
  public Object remove(String name) {
    return beans.get().remove(name);
  }

  @Override
  public void registerDestructionCallback(String name, Runnable callback) {
    destructionCallbacks.push(callback);
  }

  @Override
  public void destroy() {
    Runnable callback;
    while ((callback = destructionCallbacks.poll()) != null) {
      callback.run();
    }
  }

  @Override
  public Object resolveContextualObject(String key) {
    return null;
  }

  @Override
  public String getConversationId() {
    return Thread.currentThread().getName();
  }
}
