package com.maxwell.learning.common.annotationsexample;

import java.lang.reflect.Field;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Formatter {

    public static String format(Object object) throws IllegalAccessException {
        StringBuilder res = new StringBuilder();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Label.class)) {
                Label label = field.getAnnotation(Label.class);
                res.append(label.value()).append(":").append(field.get(object)).append(System.getProperty("line.separator"));
            }
        }
        if (res.length() > 0) {
            int separatorLength = System.getProperty("line.separator").length();
            res.delete(res.length() - separatorLength, res.length());
        }
        return res.toString();
    }
}
