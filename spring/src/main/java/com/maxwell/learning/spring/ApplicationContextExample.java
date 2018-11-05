package com.maxwell.learning.spring;

import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.support.ResourceEditorRegistrar;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.context.support.*;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class ApplicationContextExample {

    public static void main(String[] args) {

        ApplicationContext applicationContext;

        ConfigurableApplicationContext configurableApplicationContext;

        AbstractApplicationContext abstractApplicationContext;

        AbstractRefreshableApplicationContext abstractRefreshableApplicationContext;

        AbstractRefreshableConfigApplicationContext abstractRefreshableConfigApplicationContext;

        AbstractXmlApplicationContext abstractXmlApplicationContext;

        ClassPathXmlApplicationContext classPathXmlApplicationContext;

        FileSystemXmlApplicationContext fileSystemXmlApplicationContext;

        GenericApplicationContext genericApplicationContext;

        AnnotationConfigApplicationContext annotationConfigApplicationContext;

        GenericGroovyApplicationContext genericGroovyApplicationContext;

        ConfigurationClassPostProcessor configurationClassPostProcessor;

        Runnable runnable;

        Aware aware;

        BeanNameAware beanNameAware;

        BeanFactoryAware beanFactoryAware;

        ApplicationContextAware applicationContextAware;

        MessageSourceAware messageSourceAware;

        ApplicationEventPublisherAware applicationEventPublisherAware;

        ResourceLoaderAware resourceLoaderAware;

        StandardBeanExpressionResolver standardBeanExpressionResolver;

        ResourceEditorRegistrar resourceEditorRegistrar;

        //ContextTypeMatchClassLoader contextTypeMatchClassLoader;

        StandardEnvironment standardEnvironment;

        Ordered ordered;
        PriorityOrdered priorityOrdered;
    }
}
