package com.lixiang.spring.a02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TestBeanFactory {
    public static void main(String[] args) {
        // TODO Beanfactory的最重要的实现
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加Bean的定义(bean的类型，单例或多例，初始化，销毁)
        AbstractBeanDefinition beanDefinition =
                BeanDefinitionBuilder.genericBeanDefinition(Config.class)
                        .setScope("singleton").
                        getBeanDefinition();
        // 添加完BeanDefinition之后注册BeanDefinition
        beanFactory.registerBeanDefinition("config",beanDefinition);
        // 作用：给BeanFactory添加一些常用的Bean后处理器
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        // 获取Bean的后处理器 拿到这些Bean处理器后，可以对Bean工厂做扩展
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(
                beanFactoryPostProcessor -> {
                    System.out.println("processorName :"+beanFactoryPostProcessor.getClass());
            // 执行bean工厂后置处理器
            // TODO 这里是由 internalConfigurationAnnotationProcessor后置处理器解析的注解
                    /**
                     * Bean工厂后置处理器有五个
                     * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
                     * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
                     * org.springframework.context.annotation.internalCommonAnnotationProcessor
                     *      解析Resource注解
                     * org.springframework.context.event.internalEventListenerProcessor
                     * org.springframework.context.event.internalEventListenerFactory
                     */
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });
        // 上述步骤完成后，在Bean工厂里就会有一个为  config 的bean
        System.out.println("输出Bean名称");
//        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
        // 上述代码中已经将bean添加好了，下面拿出来用
        // System.out.println(beanFactory.getBean(Bean1.class).getBean2());// 在这里getBean1之后，在下面也获取不到Bean2了
        // 上述代码中打印不出来Bean2 因为没有依赖注入的功能

        // 为了完成依赖注入，下面引入Bean后置处理器  TODO  这里两个冒号表示方法引用
        // 获取Bean的后置处理器，并加入Bean工厂；TODO 这里添加完之后，在getBean的时候这些方法会加入Bean的生命周期
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        // beanFactory.preInstantiateSingletons();
        Bean1 bean = beanFactory.getBean(Bean1.class);

        System.out.println("添加Bean后置处理器后："+beanFactory.getBean(Bean1.class).getBean2());
        System.out.println("===========");

    }


    @Configuration
    static class Config {
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return new Bean4();
        }
    }

    interface Inter {

    }

    @Slf4j
    static class Bean3 implements Inter {
        public Bean3() {
            log.debug("构造 Bean3()");
        }
    }

    @Slf4j
    static class Bean4 implements Inter {
        public Bean4() {
            log.debug("构造 Bean4()");
        }
    }

    @Slf4j
    static class Bean1 {
        public Bean1() {
            log.debug("构造 Bean1()");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        private Inter getInter() {
            return bean3;
        }
    }

    @Slf4j
    static class Bean2 {
        public Bean2() {
            log.debug("构造 Bean2()");
        }
    }
}
