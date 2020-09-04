package com.minmin.wenda.async;

import com.alibaba.fastjson.JSON;
import com.minmin.wenda.async.handler.EventHandler;
import com.minmin.wenda.util.JedisAdapter;
import com.minmin.wenda.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-04
 * Time: 22:01
 */
@Service
@Slf4j
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    // TODO ApplicationContextAware InitializingBean
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() {
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> eventHandlerEntry: beans.entrySet()) {
                List<EventType> eventTypes = eventHandlerEntry.getValue().getSupportEventTypes();

                for (EventType type: eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(eventHandlerEntry.getValue());
                }
            }
        }

        Thread thread = new Thread(() -> {
            while (true) {
                String key = RedisKeyUtil.getEventQueueKey();
                List<String> events = jedisAdapter.brpop(0, key);
                for (String message: events) {
                    if (message.equals(key)) {
                        continue;
                    }

                    EventModel eventModel = JSON.parseObject(message, EventModel.class);
                    if (!config.containsKey(eventModel.getType())) {
                        log.error("Can not recognize eventType");
                    }

                    for (EventHandler handler: config.get(eventModel.getType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
