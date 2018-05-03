package com.maxwell.learning.designpattern.resposibilitychain.handler;

import com.maxwell.learning.designpattern.resposibilitychain.Target;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class NameHandler implements Handler {

    @Override
    public boolean preHandle(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s][return: %s]",
                        NameHandler.class.getSimpleName(), getOrder(), "preHandle", "true"));
        return true;
    }

    @Override
    public void handle(Target target) {
        String origin = target.getName();
        String after = "new name";
        target.setName(after);
        System.out.println(
                String.format("[%s][order: %d][method: %s][origin: %s][now: %s]",
                        NameHandler.class.getSimpleName(), getOrder(), "handle", origin, after));
    }

    @Override
    public void afterCompletion(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s]",
                        NameHandler.class.getSimpleName(), getOrder(), "afterCompletion"));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
