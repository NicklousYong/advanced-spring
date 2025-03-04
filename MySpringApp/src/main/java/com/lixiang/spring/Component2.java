package com.lixiang.spring;

import com.lixiang.common.MyLog4JFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
public class Component2 {

    static Logger myLogger  = MyLog4JFactory.getLogger();

    // TODO 这个注解表示这个方法是一个监听事件的方法
    @EventListener
    public void aaa(UserRegisteredEvent event){
        System.out.println("sss");
        myLogger.info("{"+event+"}");
        System.out.println("jsjjs");




    }


}
