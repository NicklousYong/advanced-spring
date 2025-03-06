package com.lixiang.spring;

import org.springframework.context.ApplicationEvent;

/**
 * @author lixiang
 * @date 2025/03/04  13:26
 */
// 继承自事件父类
public class UserRegisteredEvent extends ApplicationEvent  {
    // TODO source为事件源，就是谁发的事件
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
