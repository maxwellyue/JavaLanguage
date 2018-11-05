package com.maxwell.learning.common.annotationsexample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {

    SizeValue size() default SizeValue.MEDIUM;

    enum SizeValue {
        SMALL, MEDIUM, LARGE
    }

}
