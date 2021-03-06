/*
 * Copyright (C) 2014 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.maxwell.learning.event.eventbus;

import com.google.common.annotations.VisibleForTesting;
import AllowConcurrentEvents;
import EventBus;
import SubscriberExceptionContext;
import com.google.j2objc.annotations.Weak;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A subscriber method on a specific object, plus the executor that should be used for dispatching
 * events to it.
 * <p>
 * <p>Two subscribers are equivalent when they refer to the same method on the same object (not
 * class). This property is used to ensure that no subscriber method is registered more than once.
 *
 * @author Colin Decker
 */
class Subscriber {

    /**
     * Creates a {@code Subscriber} for {@code method} on {@code listener}.
     *
     * 如果方法添加了@AllowConcurrentEvents注解，则可能会多个线程同时调用method，
     * 如果没有添加该注解，则会同步调用（Synchronized）该方法，确保线程安全。
     *
     */
    static Subscriber create(EventBus bus, Object listener, Method method) {
        return isDeclaredThreadSafe(method)
                ? new Subscriber(bus, listener, method)
                : new SynchronizedSubscriber(bus, listener, method);
    }

    /**
     * 该订阅者所属的event bus
     * <p>
     * 有可能同一个应用中，使用了多个event bus
     */
    @Weak
    private EventBus bus;

    /**
     * The object with the subscriber method.
     */
    @VisibleForTesting
    final Object target;

    /**
     * 订阅方法，即接收到事件之后要执行的逻辑所在的方法
     */
    private final Method method;

    /**
     * Executor to use for dispatching events to this subscriber.
     * <p>
     * 用来将事件分发到该订阅者的执行器
     */
    private final Executor executor;

    /**
     * 内部私有构造器，默认使用event bus中的执行器
     *
     * @param bus
     * @param target
     * @param method
     */
    private Subscriber(EventBus bus, Object target, Method method) {
        this.bus = bus;
        this.target = checkNotNull(target);
        this.method = method;
        method.setAccessible(true);

        this.executor = bus.executor();
    }

    /**
     * 通过反射执行method
     */
    final void dispatchEvent(final Object event) {
        executor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            invokeSubscriberMethod(event);
                        } catch (InvocationTargetException e) {
                            bus.handleSubscriberException(e.getCause(), context(event));
                        }
                    }
                });
    }

    /**
     * 通过反射调用具体的处理事件的方法
     * <p>
     * 该方法可以通过重写，来进行同步调用具体的处理事件的方法
     */
    @VisibleForTesting
    void invokeSubscriberMethod(Object event) throws InvocationTargetException {
        try {
            method.invoke(target, checkNotNull(event));
        } catch (IllegalArgumentException e) {
            throw new Error("Method rejected target/argument: " + event, e);
        } catch (IllegalAccessException e) {
            throw new Error("Method became inaccessible: " + event, e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Gets the context for the given event.
     */
    private SubscriberExceptionContext context(Object event) {
        return new SubscriberExceptionContext(bus, event, target, method);
    }


    /**
     * method是否线程安全：使用AllowConcurrentEvents来指示
     */
    private static boolean isDeclaredThreadSafe(Method method) {
        return method.getAnnotation(AllowConcurrentEvents.class) != null;
    }

    /**
     * Subscriber that synchronizes invocations of a method to ensure that only one thread may enter
     * the method at a time.
     */
    @VisibleForTesting
    static final class SynchronizedSubscriber extends Subscriber {

        private SynchronizedSubscriber(EventBus bus, Object target, Method method) {
            super(bus, target, method);
        }

        @Override
        void invokeSubscriberMethod(Object event) throws InvocationTargetException {
            synchronized (this) {
                super.invokeSubscriberMethod(event);
            }
        }
    }

    //--------重写hashcode 和 equal 方法-----

    @Override
    public final int hashCode() {
        return (31 + method.hashCode()) * 31 + System.identityHashCode(target);
    }

    @Override
    public final boolean equals(@NullableDecl Object obj) {
        if (obj instanceof Subscriber) {
            Subscriber that = (Subscriber) obj;
            // Use == so that different equal instances will still receive events.
            // We only guard against the case that the same object is registered
            // multiple times
            return target == that.target && method.equals(that.method);
        }
        return false;
    }
}
