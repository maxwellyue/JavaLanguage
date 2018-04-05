package com.maxwell.learning.event.eventbus;

import com.google.common.annotations.Beta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ①表明订阅者的订阅方法是线程安全的，多个线程可以同时调用事件处理方法
 * ②该注解要与@Subscribe一起使用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Beta
public @interface AllowConcurrentEvents {
}
