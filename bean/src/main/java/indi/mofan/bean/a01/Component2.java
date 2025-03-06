package indi.mofan.bean.a01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author mofan
 * @date 2022/12/18 16:24
 */
@Slf4j
@Component
public class Component2 {
    @EventListener //TODO 表示监听事件 加上这个注解时，这个类成了监听器
    // 整个流程：在componet1中调用publishEvent方法发布事件，而在component2中具有ventListener 注解的方法监听事件。
    public void aaa(UserRegisteredEvent event) {
        log.debug("{}", event);
        log.debug("发送一条短信");
    }
}
