package com.maxwell.learning.designpattern.resposibilitychain.handler;

import com.maxwell.learning.designpattern.resposibilitychain.Target;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ContentHandler implements Handler {

    @Override
    public boolean preHandle(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s][return: %s]",
                        ContentHandler.class.getSimpleName(), getOrder(), "preHandle", "true"));
        return true;
    }

    @Override
    public void handle(Target target) {
        String origin = target.getContent();
        String after = "new content";
        target.setContent(after);
        System.out.println(
                String.format("[%s][order: %d][method: %s][origin: %s][now: %s]",
                        ContentHandler.class.getSimpleName(), getOrder(), "handle", origin, after));
    }

    @Override
    public void afterCompletion(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s]",
                        ContentHandler.class.getSimpleName(), getOrder(), "afterCompletion"));
    }

    @Override
    public int getOrder() {
        return 3;
    }

}
