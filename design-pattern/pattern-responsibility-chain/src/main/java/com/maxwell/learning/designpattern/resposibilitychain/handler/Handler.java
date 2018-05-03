package com.maxwell.learning.designpattern.resposibilitychain.handler;

import com.maxwell.learning.designpattern.resposibilitychain.Target;

/**
 *
 * 注意顺序
 * 如有：handler1 > handler2，且preHandle均返回true，
 * 则调用顺序为：handler1.preHandle > handler1.handle > handler2.preHandle > handler2.handle > handler2.afterCompletion > handler1.afterCompletion
 *
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public interface Handler extends Comparable<Handler>{

    /**
     * 处理之前做的操作
     * @param target 要处理的对象
     * @return 是否要处理该对象，true则处理，false则不处理
     */
    default boolean preHandle(Target target){
        return true;
    }

    /**
     * 具体的处理逻辑
     * @param target 要处理的对象
     */
    default void handle(Target target){}

    /**
     * 处理完之后的操作
     *
     * 只有preHandle返回true时，才调用
     *
     * @param target 要处理的对象
     */
    default void afterCompletion(Target target){}

    /**
     * 获取顺序
     * @return
     */
    default int getOrder(){
        return 0;
    }


    /**
     * 按照从小到大顺序（按1,2,3...顺序执行）
     * @param handler
     * @return
     */
    @Override
    default int compareTo(Handler handler) {
        return getOrder() - handler.getOrder();
    }
}
