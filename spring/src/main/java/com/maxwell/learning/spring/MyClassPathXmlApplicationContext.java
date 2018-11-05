package com.maxwell.learning.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    @Override
    protected void initPropertySources() {
        //要求必须含有该var这个配置
        getEnvironment().setRequiredProperties("var");
    }
}
