package com.maxwell.learning.common.annotationsexample;

import java.lang.annotation.*;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String type() default "";
}
