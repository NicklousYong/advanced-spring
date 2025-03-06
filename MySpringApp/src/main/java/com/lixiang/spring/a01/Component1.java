package com.lixiang.spring;

import com.lixiang.common.MyLog4JFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2022/12/18 16:23
 */
@Slf4j
@Component                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
public class Component1 {
    //事件发布器
    @Autowired
    private ApplicationEventPublisher context;
    static Logger  myLogger  = MyLog4JFactory.getLogger();
    // static  Logger  myLogger =  Logger.getLogger(Component1.class);

    public void register() {
        myLogger.debug("用户注册");
        // TODO 在这里使用事件来解耦，使用事件监听来执行注册之后的发短信之类的操作
        context.publishEvent(new UserRegisteredEvent(this));
    }

    public static void main(String[] args) {
        System.out.println("开始");

       if (myLogger.isInfoEnabled()) {
           myLogger.info("成了！！！");
       }else {
           System.out.println(myLogger.getLevel());
       }
        System.out.println("结束");
    }

}
