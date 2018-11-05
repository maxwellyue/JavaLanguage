package com.maxwell.learning.spring;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.beans.propertyeditors.PropertiesEditor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.core.AliasRegistry;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.support.ServletContextAwareProcessor;
import org.springframework.web.context.support.ServletContextResourceLoader;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class Server {

    public static void main(String[] args) {

        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring.xml"));

        CountDownLatch latch = new CountDownLatch(1);

        beanFactory.getBean("nameA");

        AutowireCapableBeanFactory autowireCapableBeanFactory;

        ListableBeanFactory listableBeanFactory;

        ConfigurableBeanFactory configurableBeanFactory;

        ConfigurableListableBeanFactory configurableListableBeanFactory;

        SingletonBeanRegistry singletonBeanRegistry;

        AliasRegistry aliasRegistry;

        DefaultListableBeanFactory defaultListableBeanFactory;

        DefaultSingletonBeanRegistry defaultSingletonBeanRegistry;

        PropertiesEditor propertiesEditor;

        PropertyEditorSupport propertyEditorSupport;

        PropertyEditorRegistry propertyEditorRegistry;

        AbstractPropertyAccessor abstractPropertyAccessor;

        PropertyValue propertyValue;
        PropertyValues propertyValues;

        PropertyResolver propertyResolver;

        BeanWrapper beanWrapper;

        BeanWrapperImpl beanWrapper1;

        PropertyAccessorFactory propertyAccessorFactory;


        AbstractBeanDefinition abstractBeanDefinition;

        BeanMetadataAttribute beanMetadataAttribute;

        BeanMetadataElement beanMetadataElement;

        BeanMetadataAttributeAccessor beanMetadataAttributeAccessor;

        AbstractNestablePropertyAccessor abstractNestablePropertyAccessor;


        Resource resource;
        WritableResource writableResource;

        AbstractResource abstractResource;
        ByteArrayResource byteArrayResource;
        InputStreamResource inputStreamResource;
        DescriptiveResource descriptiveResource;
        VfsResource vfsResource;

        AbstractFileResolvingResource abstractFileResolvingResource;

        ClassPathResource classPathResource;

        UrlResource urlResource;

        PathResource pathResource;

        FileSystemResource fileSystemResource;

        FileUrlResource fileUrlResource;


        ResourceLoader resourceLoader;

        DefaultResourceLoader defaultResourceLoader;

        FileSystemResourceLoader fileSystemResourceLoader;

        ServletContextResourceLoader servletContextResourceLoader;

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver;


        BeanFactoryPostProcessor beanFactoryPostProcessor;
        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer;

        CustomScopeConfigurer customScopeConfigurer;

        CustomEditorConfigurer customEditorConfigurer;

        DeprecatedBeanWarner deprecatedBeanWarner;

        BeanPostProcessor beanPostProcessor;

        DestructionAwareBeanPostProcessor destructionAwareBeanPostProcessor;

        MergedBeanDefinitionPostProcessor mergedBeanDefinitionPostProcessor;

        CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor;
        AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;

        RequiredAnnotationBeanPostProcessor requiredAnnotationBeanPostProcessor;

        //ApplicationContextAwareProcessor applicationContextAwareProcessor;

        BeanValidationPostProcessor beanValidationPostProcessor;

        MethodValidationPostProcessor methodValidationPostProcessor;

        AbstractAutoProxyCreator abstractAutoProxyCreator;

        AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator;

        ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

        AsyncAnnotationBeanPostProcessor asyncAnnotationBeanPostProcessor;

        ServletContextAwareProcessor servletContextAwareProcessor;

        try {
            latch.await();
        } catch (InterruptedException ignored) {
        }
    }

    public static Map<String, Object> beanMap = new HashMap<>();
    public static Map<String, Object> earlyBeanMap = new HashMap<>();
    public static Map<String, ObjectFactory> objectFactoryMap = new HashMap<>();


    /*public Object getBean(String beanName) {
        Object bean = getSingleton(beanName);
        if (bean == null) {
            Object dependOnBean = getBean("depends on");
            bean = getSingleton(beanName);
        }
        set(bean, dependOnBean);
        return bean;
    }

    public void set(Object bean, Object dependOnBean){
        bean.setDependOn(dependOnBean);
    }*/

    public Object getSingleton(String beanName) {
        Object bean = beanMap.get(beanName);
        if (bean == null) {
            bean = earlyBeanMap.get(beanName);
            if (bean == null) {
                ObjectFactory singletonFactory = objectFactoryMap.get(beanName);
                if (singletonFactory != null) {
                    bean = singletonFactory.getObject();
                    earlyBeanMap.put(beanName, bean);
                    objectFactoryMap.remove(beanName);
                } else {
                    ObjectFactory objectFactory = new ObjectFactory() {
                        @Override
                        public Object getObject() throws BeansException {
                            return null;
                        }
                    };
                    objectFactoryMap.put(beanName, objectFactory);
                }
            }
        }
        return bean;
    }


}
