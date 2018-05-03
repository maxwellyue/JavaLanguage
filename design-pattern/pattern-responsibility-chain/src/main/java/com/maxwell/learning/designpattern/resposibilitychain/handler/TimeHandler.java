package com.maxwell.learning.designpattern.resposibilitychain.handler;

import com.maxwell.learning.designpattern.resposibilitychain.Target;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class TimeHandler implements Handler {

    @Override
    public boolean preHandle(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s][return: %s]",
                        TimeHandler.class.getSimpleName(),getOrder(), "preHandle", "true"));
        return true;
    }

    @Override
    public void handle(Target target) {
        Date origin = target.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(origin);
        calendar.add(Calendar.DATE, 1);
        Date after = calendar.getTime();
        target.setTime(after);

        System.out.println(
                String.format("[%s][order: %d][method: %s][origin: %s][now: %s]",
                        TimeHandler.class.getSimpleName(),getOrder(), "handle", origin.toLocaleString(), after.toLocaleString()));
    }

    @Override
    public void afterCompletion(Target target) {
        System.out.println(
                String.format("[%s][order: %d][method: %s]",
                        TimeHandler.class.getSimpleName(),getOrder(), "afterCompletion"));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
