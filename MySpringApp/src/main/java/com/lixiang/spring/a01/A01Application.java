package com.lixiang.spring.a01;


import com.lixiang.spring.Component1;
import com.lixiang.spring.UserRegisteredEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

@Slf4j
@SpringBootApplication//引导类的注解
public class A01Application {
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        // SpringApplication.run 返回Spring容器
        /**
         * Ctrl+U可看到继承图，SpringApplication其实是继承了BeanFactory
         * ApplcicationContext其实是组合了BeanFactory的方法。在AbatractApplicationContext内部，getBean方法实际上是
         * 使用了beanFactiry的getBean方法（组合模式）
         *
         */
        /**
         * BeanFactory能干啥
         *      表面上只有getBean
         *      实际上控制反转，基本的依赖注入，直至Bean的生命周期的各种功能，都由他的实现类(DefaultListableBeanFactory)提供
         *
         *  ApplicationContext的扩展体现在四个父接口上
         *      1.MessageResource
         *          处理国际化资源的能力(翻译)
         *      2.Resource4PatternResolver    context.getResources
         *          通配符，处理磁盘资源
         *      3.ApplicationEventPublisher
         *          时间对象发布器，用于发布事件对象
         *      4.EnvironmentCapable        context.getEnvironment().getProperty("server.port")
         *          读取环境变量
         *
         */
        ConfigurableApplicationContext context = SpringApplication.run(A01Application.class, args);
        System.out.println(context);
        System.out.println(context.getMessage("hi",null,Locale.CHINA));//根据固定的key获取翻译后的结果，翻译的信息存储在messages开头的文件中
        System.out.println(context.getMessage("hi",null,Locale.ENGLISH));
        System.out.println(context.getMessage("hi",null,Locale.JAPANESE));
        // todo 在classPath的冒号前加一个星号，可以找到jar包里面的内容
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        System.out.println(resources);
        System.out.println("==================");

        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));
        context.publishEvent(new UserRegisteredEvent(context));
        // TODO 上述为发送事件的代码，其实还有收事件的代码，即 监听器
        // 这里调用Component1的register方法，注册后触发事件。
        context.getBean(Component1.class).register();
        System.out.println("+++++++++++++++++++++");




//            Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
//            singletonObjects.setAccessible(true);
//            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
//            Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
//            map.entrySet().stream().filter(e -> e.getKey().startsWith("component"))
//                    .forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
//
//            System.out.println(context.getMessage("thanks", null, Locale.ENGLISH));
//            System.out.println(context.getMessage("thanks", null, Locale.SIMPLIFIED_CHINESE));
//            System.out.println(context.getMessage("thanks", null, Locale.TRADITIONAL_CHINESE));

//            Resource[] resources = context.getResources("classpath:application.properties");
//            Assert.isTrue(resources.length > 0, "加载类路径下的 application.properties 文件失败");
//
//            // 使用 classpath* 可以加载 jar 里类路径下的 resource
//            resources = context.getResources("classpath*:META-INF/spring.factories");
//            Assert.isTrue(resources.length > 0, "加载类路径下的 META-INF/spring.factories 文件失败");
//
//            System.out.println(context.getEnvironment().getProperty("java_home"));
//            System.out.println(context.getEnvironment().getProperty("properties.name"));
//
//            context.getBean(Component1.class).register();
        context.close();
    }
}


