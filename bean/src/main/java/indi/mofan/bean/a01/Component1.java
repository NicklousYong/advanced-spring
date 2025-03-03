package indi.mofan.bean.a01;

import com.example.logConfigure.MyLog4JFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
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
    @Autowired
    private ApplicationEventPublisher context;
    static Logger Component1Logger =  MyLog4JFactory.getLogger();

    public void register() {
        Component1Logger.debug("用户注册");
        context.publishEvent(new UserRegisteredEvent(this));
    }

    public static void main(String[] args) {
        Component1Logger.info("开始。。。");
    }

}
