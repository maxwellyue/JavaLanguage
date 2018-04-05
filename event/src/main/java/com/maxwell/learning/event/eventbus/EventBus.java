package com.maxwell.learning.event.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.util.concurrent.MoreExecutors;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Dispatches events to listeners, and provides ways for listeners to register themselves.
 * <p>
 * <p>The EventBus allows publish-subscribe-style communication between components without requiring
 * the components to explicitly register with one another (and thus be aware of each other). It is
 * designed exclusively to replace traditional Java in-process event distribution using explicit
 * registration. It is <em>not</em> a general-purpose publish-subscribe system, nor is it intended
 * for interprocess communication.
 * <p>
 * <h2>Receiving Events</h2>
 * <p>
 * <p>To receive events, an object should:
 * <p>
 * <ol>
 * <li>Expose a public method, known as the <i>event subscriber</i>, which accepts a single
 * argument of the type of event desired;
 * <li>Mark it with a {@link Subscribe} annotation;
 * <li>Pass itself to an EventBus instance's {@link #register(Object)} method.
 * </ol>
 * <p>
 * <h2>Posting Events</h2>
 * <p>
 * <p>To post an event, simply provide the event object to the {@link #post(Object)} method. The
 * EventBus instance will determine the type of event and route it to all registered listeners.
 * <p>
 * <p>Events are routed based on their type &mdash; an event will be delivered to any subscriber for
 * any type to which the event is <em>assignable.</em> This includes implemented interfaces, all
 * superclasses, and all interfaces implemented by superclasses.
 * <p>
 * <p>When {@code post} is called, all registered subscribers for an event are run in sequence, so
 * subscribers should be reasonably quick. If an event may trigger an extended process (such as a
 * database load), spawn a thread or queue it for later. (For a convenient way to do this, use an
 * {@link AsyncEventBus}.)
 * <p>
 * <h2>Subscriber Methods</h2>
 * <p>
 * <p>Event subscriber methods must accept only one argument: the event.
 * <p>
 * <p>Subscribers should not, in general, throw. If they do, the EventBus will catch and log the
 * exception. This is rarely the right solution for error handling and should not be relied upon; it
 * is intended solely to help find problems during development.
 * <p>
 * <p>The EventBus guarantees that it will not call a subscriber method from multiple threads
 * simultaneously, unless the method explicitly allows it by bearing the {@link
 * AllowConcurrentEvents} annotation. If this annotation is not present, subscriber methods need not
 * worry about being reentrant, unless also called from outside the EventBus.
 * <p>
 * <h2>Dead Events</h2>
 * <p>
 * <p>If an event is posted, but no registered subscribers can accept it, it is considered "dead."
 * To give the system a second chance to handle dead events, they are wrapped in an instance of
 * {@link DeadEvent} and reposted.
 * <p>
 * <p>If a subscriber for a supertype of all events (such as Object) is registered, no event will
 * ever be considered dead, and no DeadEvents will be generated. Accordingly, while DeadEvent
 * extends {@link Object}, a subscriber registered to receive any Object will never receive a
 * DeadEvent.
 * <p>
 * <p>This class is safe for concurrent use.
 * <p>
 * <p>See the Guava User Guide article on <a
 * href="https://github.com/google/guava/wiki/EventBusExplained">{@code EventBus}</a>.
 *
 * @author Cliff Biffle
 * @since 10.0
 */
@Beta
public class EventBus {

    /**
     * 整个工作流程可以分解为：
     *
     * ①事件注册；②事件分发；③处理事件
     *
     * 当有事件发生时（对应EventBus中的post(Object event)方法），
     * 将event对象传递给订阅者(这一步就是所谓的调度或分发，对应Dispatcher的dispatch(Object event, Iterator<Subscriber> subscribers)方法)，
     * 然后让订阅者去处理该事件（这一步就是执行器Executor的工作，它会去调用用户写的处理方法）
     * <p>
     * 问题就来了：
     * ①当事件发生时，将event对象传递给订阅者，怎么传递？这就是不同的分发策略
     * ②订阅者接收到该事件后，如果去执行处理逻辑，是同步还是异步？
     *
     * 带着这些问题去读代码会更清晰、简单
     *
     *-------------------------------------------------------
     * 整个过程是从发生事件触发的，即post(Object event)方法
     *
     * 对于EventBus
     * 假如在线程A中执行post(Object event)，则接下来的分发、调用都会在该线程中进行；
     * 而且，可以保证事件post的顺序与调用顺序是一致的
     *
     *
     * 对于AsyncEventBus
     * 假如在线程A中执行post(Object event)，则接下来的分发会在该线程中进行，
     * 但调用则不一定会在该线程中进行，而是由创建AsyncEventBus时传入的Executor来执行，
     * 假如Executor依然为MoreExecutors.directExecutor()，则调用仍然发生在线程A中，
     * 假如Executor为其他，则调用由Executor（一般为线程池）来执行。
     * 不能保证事件post的顺序与调用顺序是一致的
     *
     *
     * ①event(事件发生)--->②dispatch(事件分发)--->③executor(调用监听方法)
     * 我觉得在AsyncEventBus中，只有第③阶段是异步进行的 todo 但官方注释好像看起来并不是
     *
     *
     *
     * 并发可能发生的地方：
     *
     * ①多个线程同时进行post(Object event)，且事件类型一样：
     * 此时，可能会同时调用用户写的处理事件的逻辑代码（即加了@Subscribe注解的方法），
     * 如果该方法上没有@AllowConcurrentEvents注解，则会添加synchronized进行同步
     * 如果该方法上有@AllowConcurrentEvents注解，则会同时调用该方法
     *
     * ②多个线程同时注册（register(Object object)）：
     * SubscriberRegistry中使用CopyOnWriteArraySet来保存同一类型事件的处理方法，不会有问题
     * todo SubscriberRegistry的代码没完全读
     *
     * ---------------------------------------------------------
     *
     *
     *
     */

    private static final Logger logger = Logger.getLogger(EventBus.class.getName());

    /**
     * 该EventBus的身份，即同一应用中，可使用了多个EventBus，可以使用该属性来区分
     */
    private final String identifier;
    /**
     * 执行器：真正去execute订阅者的订阅方法
     */
    private final Executor executor;
    /**
     * 异常处理器：处理订阅者抛出的异常
     */
    private final SubscriberExceptionHandler exceptionHandler;
    /**
     * 订阅注册表：记录该event bus中事件与订阅者之间的关系
     **/
    private final SubscriberRegistry subscribers = new SubscriberRegistry(this);
    /**
     * 事件分发器或调度器：定义事件的分发策略，
     */
    private final Dispatcher dispatcher;

    //------------------ 构造方法 ----------------------------
    /**
     *
     * 在这些构造方法中，指定了EventBus的Executor和Dispatcher的类型。
     *
     *  Executor：
     *      MoreExecutors.directExecutor()：
     *      行为：runs each task in the thread that invokes {@link Executor#execute execute}
     *
     *  Dispatcher：PerThreadQueuedDispatcher
     *
     */


    /**
     * 创建name为"default"的EventBus
     */
    public EventBus() {
        this("default");
    }

    /**
     * 创建name为identifier的EventBus
     */
    public EventBus(String identifier) {
        this(
                identifier,
                MoreExecutors.directExecutor(),
                Dispatcher.perThreadDispatchQueue(),
                LoggingHandler.INSTANCE);
    }

    /**
     * 创建name为"default"的EventBus，并指定订阅异常处理器
     */
    public EventBus(SubscriberExceptionHandler exceptionHandler) {
        this(
                "default",
                MoreExecutors.directExecutor(),
                Dispatcher.perThreadDispatchQueue(),
                exceptionHandler);
    }

    EventBus(
            String identifier,
            Executor executor,
            Dispatcher dispatcher,
            SubscriberExceptionHandler exceptionHandler) {
        this.identifier = checkNotNull(identifier);
        this.executor = checkNotNull(executor);
        this.dispatcher = checkNotNull(dispatcher);
        this.exceptionHandler = checkNotNull(exceptionHandler);
    }


    /**
     * 将所有object中的订阅方法（即加了@Subscribe注解的方法）进行注册
     */
    public void register(Object object) {
        subscribers.register(object);
    }

    /**
     * 将所有object中的订阅方法（即加了@Subscribe注解的方法）取消注册
     */
    public void unregister(Object object) {
        subscribers.unregister(object);
    }


    /**
     * 将事件post给所有的订阅者，该方法在成功将事件post给所有订阅者后即返回，不管有没有订阅者有没有抛出异常。
     * <p>
     * 如果该event没有订阅者，并且该event不是DeadEvent，则会将该event包装成DeadEvent，并重新post。
     */
    public void post(Object event) {
        //根据事件类型，从注册表中取出该类型事件的所有订阅者
        Iterator<Subscriber> eventSubscribers = subscribers.getSubscribers(event);
        //如果订阅者不为空
        if (eventSubscribers.hasNext()) {
            //将事件分发给所有的订阅者
            dispatcher.dispatch(event, eventSubscribers);
        } else if (!(event instanceof DeadEvent)) {//如果订阅者不为空，并且事件不是DeadEvent
            //重新post一次
            post(new DeadEvent(this, event));
        }
    }

    public final String identifier() {
        return identifier;
    }

    final Executor executor() {
        return executor;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(identifier).toString();
    }

    //-----------------订阅者抛出的异常的处理-----------------

    /**
     * 处理订阅者抛出的异常
     *
     * @param e
     * @param context
     */
    void handleSubscriberException(Throwable e, SubscriberExceptionContext context) {
        checkNotNull(e);
        checkNotNull(context);
        try {
            exceptionHandler.handleException(e, context);
        } catch (Throwable e2) {
            // if the handler threw an exception... well, just log it
            logger.log(
                    Level.SEVERE,
                    String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", e2, e),
                    e2);
        }
    }

    /**
     * 订阅者抛出的异常的处理器的实现，日志处理器，处理方式就是打印异常信息
     */
    static final class LoggingHandler implements SubscriberExceptionHandler {
        static final LoggingHandler INSTANCE = new LoggingHandler();

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            Logger logger = logger(context);
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, message(context), exception);
            }
        }

        private static Logger logger(SubscriberExceptionContext context) {
            return Logger.getLogger(EventBus.class.getName() + "." + context.getEventBus().identifier());
        }

        private static String message(SubscriberExceptionContext context) {
            Method method = context.getSubscriberMethod();
            return "Exception thrown by subscriber method "
                    + method.getName()
                    + '('
                    + method.getParameterTypes()[0].getName()
                    + ')'
                    + " on subscriber "
                    + context.getSubscriber()
                    + " when dispatching event: "
                    + context.getEvent();
        }
    }
}
