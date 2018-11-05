package com.maxwell.learning.spring.sourcecode;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class InvokeBeanFactoryPostProcessors {

    /*public static void invokeBeanFactoryPostProcessors(
            ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

        // 记录已经处理过的Bean
        Set<String> processedBeans = new HashSet<>();

        //如果beanFactory实现了BeanDefinitionRegistry
        if (beanFactory instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
            //记录常规的BeanFactoryPostProcessor
            List<BeanFactoryPostProcessor> regularPostProcessors = new LinkedList<>();
            //记录类型为BeanDefinitionRegistryPostProcessor的这种特殊的BeanFactoryPostProcessor
            List<BeanDefinitionRegistryPostProcessor> registryProcessors = new LinkedList<>();
            //处理参数传入的BeanFactoryPostProcessor
            for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
                //如果是BeanDefinitionRegistryPostProcessor类型的，立即回调其中的postProcessBeanDefinitionRegistry，并记下来
                if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
                    BeanDefinitionRegistryPostProcessor registryProcessor =
                            (BeanDefinitionRegistryPostProcessor) postProcessor;
                    registryProcessor.postProcessBeanDefinitionRegistry(registry);
                    registryProcessors.add(registryProcessor);
                }
                else {//如果是常规类型，则记下来，此处不回调
                    regularPostProcessors.add(postProcessor);
                }
            }
            //处理beanFactory（的BeanDefinition注册中心）中的BeanDefinitionRegistryPostProcessor

            //记录beanFactory中BeanDefinitionRegistryPostProcessor类型的BeanFactoryPostProcessor
            List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

            //寻找实现了PriorityOrdered的BeanDefinitionRegistryPostProcessor，
            //将其记录到currentRegistryProcessors中，并进行排序
            //并回调
            String[] postProcessorNames =
                    beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            for (String ppName : postProcessorNames) {
                if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
                    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(ppName);
                }
            }
            sortPostProcessors(currentRegistryProcessors, beanFactory);
            registryProcessors.addAll(currentRegistryProcessors);
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            currentRegistryProcessors.clear();

            //寻找实现了Ordered的BeanDefinitionRegistryPostProcessor，
            //将其记录到currentRegistryProcessors中，并进行排序
            //并回调
            postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            for (String ppName : postProcessorNames) {
                if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
                    currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    processedBeans.add(ppName);
                }
            }
            sortPostProcessors(currentRegistryProcessors, beanFactory);
            registryProcessors.addAll(currentRegistryProcessors);
            invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
            currentRegistryProcessors.clear();

            //寻找其他的（未实现排序接口的）BeanDefinitionRegistryPostProcessor，
            //将其记录到currentRegistryProcessors中，并进行排序
            //并回调
            boolean reiterate = true;
            while (reiterate) {
                reiterate = false;
                postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
                for (String ppName : postProcessorNames) {
                    if (!processedBeans.contains(ppName)) {
                        currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                        processedBeans.add(ppName);
                        reiterate = true;
                    }
                }
                sortPostProcessors(currentRegistryProcessors, beanFactory);
                registryProcessors.addAll(currentRegistryProcessors);
                invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
                currentRegistryProcessors.clear();
            }

            // 按照顺序，循环调用参数传入的以及在beanFactory中找到的BeanFactoryPostProcessor的postProcessBeanFactory方法
            invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
            invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
            // 到这里，所有的BeanDefinitionRegistryPostProcessor（所有的方法）都已经回调完成，
            // 而普通类型的BeanFactoryPostProcessor，仅仅回调了在参数中传入的（还没有在beanFactory中寻找普通类型的BeanFactoryPostProcessor）
        }

        else {
            // 如果beanFactory没有实现BeanDefinitionRegistry，则回调在参数中传入的BeanFactoryPostProcessor
            invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
        }

        //在beanFactory中寻找普通类型的BeanFactoryPostProcessor
        String[] postProcessorNames =
                beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

        // 将普通类型的BeanFactoryPostProcessor分为3类：
        // ①实现了PriorityOrdered
        // ②实现了Ordered
        // ③其他
        List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
        List<String> orderedPostProcessorNames = new ArrayList<>();
        List<String> nonOrderedPostProcessorNames = new ArrayList<>();
        for (String ppName : postProcessorNames) {
            if (processedBeans.contains(ppName)) {
                // skip - already processed in first phase above
                // 跳过之前已经回调过的BeanDefinitionRegistryPostProcessor，
                // 以及参数传入过的普通BeanFactoryPostProcessor
            }
            else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
                priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
            }
            else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
                orderedPostProcessorNames.add(ppName);
            }
            else {
                nonOrderedPostProcessorNames.add(ppName);
            }
        }

        // 回调实现了PriorityOrdered的普通类型BeanFactoryPostProcessor
        sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
        invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

        // 回调实现了Ordered的普通类型BeanFactoryPostProcessor
        List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>();
        for (String postProcessorName : orderedPostProcessorNames) {
            orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
        }
        sortPostProcessors(orderedPostProcessors, beanFactory);
        invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

        // 回调其他的（未实现PriorityOrdered，也未实现Ordered的）普通类型BeanFactoryPostProcessor
        List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>();
        for (String postProcessorName : nonOrderedPostProcessorNames) {
            nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
        }
        invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

        // Clear cached merged bean definitions since the post-processors might have
        // modified the original metadata, e.g. replacing placeholders in values...
        beanFactory.clearMetadataCache();
    }*/


}
