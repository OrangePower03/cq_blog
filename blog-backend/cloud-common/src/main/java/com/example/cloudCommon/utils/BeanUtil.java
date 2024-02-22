package com.example.cloudCommon.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BeanUtil implements BeanDefinitionRegistryPostProcessor {
    private static DefaultListableBeanFactory beanFactory;

    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    public static Object getBean(Class<?> requireType) {
        return beanFactory.getBean(requireType);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
    }
}
