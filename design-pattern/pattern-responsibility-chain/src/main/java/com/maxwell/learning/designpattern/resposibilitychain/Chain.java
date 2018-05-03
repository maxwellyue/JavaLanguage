package com.maxwell.learning.designpattern.resposibilitychain;

import com.maxwell.learning.designpattern.resposibilitychain.handler.Handler;

import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

/**
 *
 * not thread-safe
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Chain {

    private static final TreeSet<Handler> handlers = new TreeSet<>();

    public void add(Handler handler) {
        handlers.add(handler);
    }

    public void remove(Handler handler) {
        handlers.remove(handler);
    }

    public void execute(Target target) {
        Iterator<Handler> iterator = handlers.iterator();

        Stack<Handler> stack = new Stack<>();

        while (iterator.hasNext()) {
            Handler handler = iterator.next();
            boolean thisHandle = handler.preHandle(target);
            if (thisHandle) {
                handler.handle(target);
                stack.push(handler);
            }
        }

        while (!stack.isEmpty()) {
            Handler handler = stack.pop();
            handler.afterCompletion(target);
        }
    }

}
