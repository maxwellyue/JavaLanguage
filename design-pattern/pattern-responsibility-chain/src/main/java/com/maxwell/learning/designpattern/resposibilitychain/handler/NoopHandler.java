package com.maxwell.learning.designpattern.resposibilitychain.handler;

import com.maxwell.learning.designpattern.resposibilitychain.Target;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class NoopHandler implements Handler {

    @Override
    public boolean preHandle(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s][return: %s]",
                        NoopHandler.class.getSimpleName(), getOrder(), "preHandle", "false"));
        return false;
    }

    @Override
    public void handle(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s]",
                        NoopHandler.class.getSimpleName(), getOrder(), "handle"));
    }

    @Override
    public void afterCompletion(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s]",
                        NoopHandler.class.getSimpleName(), getOrder(), "afterCompletion"));
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
