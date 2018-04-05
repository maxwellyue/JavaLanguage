package com.maxwell.learning.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;

/**
 *
 *
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class GuavaEventBusTest {


    public static void main(String[] args) {

        Executors.newSingleThreadScheduledExecutor();

        testAsyncEventBus();
    }


    public static void testAsyncEventBus() {
        AsyncEventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(3));

        eventBus.register(new MySubscriber());

        eventBus.post(new MyEvent(String.format("event:[only], posted by [%s]", "main")));

        System.out.println("end");
    }

    public static void testEventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(new MySubscriber());
        for (int i = 1; i <= 1000000; i++) {
            eventBus.post(new MyEvent(String.format("event:[%d], posted by [%s]", i, "main")));
        }
        System.out.println("end");
    }


    static class MyThread extends Thread {

        private String name;
        private EventBus eventBus;


        MyThread(String name, EventBus eventBus) {
            this.name = name;
            this.eventBus = eventBus;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                eventBus.post(new MyEvent(String.format("event:[%d], posted by [%s]", i, name)));
            }
        }
    }


    static class MyEvent {

        private String msg;

        public MyEvent(String msg) {
            this.msg = msg;
        }


        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    static class MySubscriber {
        @Subscribe
        public void handlerEvent(MyEvent event) {
            System.out.println(event);
        }
    }


}
