package com.maxwell.learning.event.eventbus;

import com.google.common.collect.Queues;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Handler for dispatching events to subscribers, providing different event ordering guarantees that
 * make sense for different situations.
 * <p>
 * <p><b>Note:</b> The dispatcher is orthogonal to the subscriber's {@code Executor}. The dispatcher
 * controls the order in which events are dispatched, while the executor controls how (i.e. on which
 * thread) the subscriber is actually called when an event is dispatched to it.
 *
 * 负责将事件分发给订阅者（们），提供不同的事件顺序
 *
 * 注意：这里的Dispatcher与订阅者的Executor含义不同：
 * Dispatcher控制事件的分发顺序，而订阅者的Executor负责当事件分发到订阅者后，订阅者如何执行（比如，在哪个线程中执行）。
 *
 *
 * todo 既然都是线程安全的，两种分发方式有什么区别？
 *
 * PerThreadQueuedDispatcher：外层遍历队列中的事件，内层该事件的订阅处理器。
 * LegacyAsyncDispatcher：前后两个遍历：前一个遍历事件订阅处理器，并构建一个事件实体对象存入队列。
 * 后一个循环是遍历该事件实体对象队列，取出事件实体对象中的事件进行分发。
 *
 *
 *
 * @author Colin Decker
 */
abstract class Dispatcher {

    /**
     * Returns a dispatcher that queues events that are posted reentrantly on a thread that is already
     * dispatching an event, guaranteeing that all events posted on a single thread are dispatched to
     * all subscribers in the order they are posted.
     * <p>
     * <p>When all subscribers are dispatched to using a <i>direct</i> executor (which dispatches on
     * the same thread that posts the event), this yields a breadth-first dispatch order on each
     * thread. That is, all subscribers to a single event A will be called before any subscribers to
     * any events B and C that are posted to the event bus by the subscribers to A.
     *
     *
     *
     * 保证同一线程中post的所有事件，会按照post的顺序来分发给订阅者。
     *
     *
     *
     *
     */
    static Dispatcher perThreadDispatchQueue() {
        return new PerThreadQueuedDispatcher();
    }

    /**
     * Returns a dispatcher that queues events that are posted in a single global queue. This behavior
     * matches the original behavior of AsyncEventBus exactly, but is otherwise not especially useful.
     * For async dispatch, an {@linkplain #immediate() immediate} dispatcher should generally be
     * preferable.
     *
     * 该Dispatcher会将所有事件都保存在一个全局队列中。这种分发器适用于AsyncEventBus。
     *
     * 通常来说，为了异步分发，immediate类型的分发器是最好的。
     *
     *
     */
    static Dispatcher legacyAsync() {
        return new LegacyAsyncDispatcher();
    }

    /**
     * Returns a dispatcher that dispatches events to subscribers immediately as they're posted
     * without using an intermediate queue to change the dispatch order. This is effectively a
     * depth-first dispatch order, vs. breadth-first when using a queue.
     *
     * 该Dispatcher不会保存事件，一旦post了事件，则会立即将事件分发给订阅者。
     *
     * 这是一种深度优先的分发顺序，而使用队列时，则是一种广度优先的分发顺序。
     *
     */
    static Dispatcher immediate() {
        return ImmediateDispatcher.INSTANCE;
    }

    /**
     * 将事件event分发给订阅者subscribers，具体实现在子类中
     *
     * 有三个实现类：
     *
     * PerThreadQueuedDispatcher
     *
     * LegacyAsyncDispatcher
     *
     * ImmediateDispatcher
     *
     */
    abstract void dispatch(Object event, Iterator<Subscriber> subscribers);

    /**
     *
     * EventBus中默认的分发器
     *
     * 该分发器可以保证多个线程多次post事件时，同一线程内的事件分发顺序与事件post的顺序相同。
     *
     */
    private static final class PerThreadQueuedDispatcher extends Dispatcher {

        /**
         *
         * 每个线程都维护一个队列，保存将要分发的事件
         *
         * 通过ThreadLocal实现
         *
         */
        private final ThreadLocal<Queue<Event>> queue =
                new ThreadLocal<Queue<Event>>() {
                    @Override
                    protected Queue<Event> initialValue() {
                        return Queues.newArrayDeque();
                    }
                };

        /**
         *
         * 每个线程的分发状态，用来避免重复事件分发
         *
         */
        private final ThreadLocal<Boolean> dispatching =
                new ThreadLocal<Boolean>() {
                    @Override
                    protected Boolean initialValue() {
                        return false;
                    }
                };

        @Override
        void dispatch(Object event, Iterator<Subscriber> subscribers) {
            //事件和订阅者的为空校验
            checkNotNull(event);
            checkNotNull(subscribers);

            //获取当前线程的事件队列
            Queue<Event> queueForThread = queue.get();

            //让Event入队
            queueForThread.offer(new Event(event, subscribers));

            //设置当前线程的分发状态
            if (!dispatching.get()) {//如果是未分发状态
                dispatching.set(true);//设置为正在分发状态
                try {
                    Event nextEvent;
                    //循环取出当前线程事件队列中的所有Event todo 为什么是while，而不是if
                    while ((nextEvent = queueForThread.poll()) != null) {
                        while (nextEvent.subscribers.hasNext()) {
                            //将真正的事件交给订阅者去执行
                            nextEvent.subscribers.next().dispatchEvent(nextEvent.event);
                        }
                    }
                } finally {
                    //清空当前线程的ThreadLocal变量的值，防止内存泄漏
                    dispatching.remove();
                    queue.remove();
                }
            }
        }

        /**
         * 封装事件和订阅者
         */
        private static final class Event {
            private final Object event;
            private final Iterator<Subscriber> subscribers;

            private Event(Object event, Iterator<Subscriber> subscribers) {
                this.event = event;
                this.subscribers = subscribers;
            }
        }
    }

    /**
     *
     * AsyncEventBus中默认的分发器
     *
     * This dispatcher matches the original dispatch behavior of AsyncEventBus
     *
     * 在多线程环境下，该Dispatcher不会保证分发顺序，原因如下：
     *
     * 1、订阅者和不同线程中post的事件之间可以相互交错：
     *
     * 2、订阅者被添加到该queue上的顺序与订阅者分发到事件的顺序可能不同。当一个线程取走了queue的头元素，
     * 而马上另一个线程取走了下一个元素，这是很有可能的。
     *
     *
     *
     *
     */
    private static final class LegacyAsyncDispatcher extends Dispatcher {


        //
        // We can't really make any guarantees about the overall dispatch order for this dispatcher in
        // a multithreaded environment for a couple reasons:
        //
        // 1. Subscribers to events posted on different threads can be interleaved with each other
        //    freely. (A event on one thread, B event on another could yield any of
        //    [a1, a2, a3, b1, b2], [a1, b2, a2, a3, b2], [a1, b2, b3, a2, a3], etc.)
        // 2. It's possible for subscribers to actually be dispatched to in a different order than they
        //    were added to the queue. It's easily possible for one thread to take the head of the
        //    queue, immediately followed by another thread taking the next element in the queue. That
        //    second thread can then dispatch to the subscriber it took before the first thread does.
        //
        // All this makes me really wonder if there's any value in queueing here at all. A dispatcher
        // that simply loops through the subscribers and dispatches the event to each would actually
        // probably provide a stronger order guarantee, though that order would obviously be different
        // in some cases.

        /**
         * 全局事件队列（EventWithSubscriber: one event one subscriber）
         */
        private final ConcurrentLinkedQueue<EventWithSubscriber> queue =
                Queues.newConcurrentLinkedQueue();

        @Override
        void dispatch(Object event, Iterator<Subscriber> subscribers) {
            checkNotNull(event);
            while (subscribers.hasNext()) {
                queue.add(new EventWithSubscriber(event, subscribers.next()));
            }

            EventWithSubscriber e;
            while ((e = queue.poll()) != null) {
                e.subscriber.dispatchEvent(e.event);
            }
        }

        private static final class EventWithSubscriber {
            private final Object event;
            private final Subscriber subscriber;

            private EventWithSubscriber(Object event, Subscriber subscriber) {
                this.event = event;
                this.subscriber = subscriber;
            }
        }
    }

    /**
     * 一旦有事件发生，就会立即触发事件处理。
     */
    private static final class ImmediateDispatcher extends Dispatcher {

        //单例化
        private static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();

        @Override
        void dispatch(Object event, Iterator<Subscriber> subscribers) {
            checkNotNull(event);
            //直接将事件分发给所有的订阅者
            while (subscribers.hasNext()) {
                subscribers.next().dispatchEvent(event);
            }
        }
    }
}
