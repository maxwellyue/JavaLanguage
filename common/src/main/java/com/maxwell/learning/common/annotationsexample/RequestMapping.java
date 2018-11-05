package com.maxwell.learning.common.annotationsexample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    RequestMethod method();

    String path() default "";
}
