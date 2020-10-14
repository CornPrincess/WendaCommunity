package com.minmin.wenda.async.handler;

import com.minmin.wenda.async.EventModel;
import com.minmin.wenda.async.EventType;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-04
 * Time: 21:59
 */
public interface EventHandler {
    // 扩展性好，通过 applicationContext.getBeansOfType(EventHandler.class); 可以直接获取所有实现这个接口的类
    // 减少重复代码
    // 一些很耗时很重的代码可以通过异步队列来优化
    // 优先队列可以实现任务优先级的划分，如防止某人一直提交代码，这样他就不会占用队列
    void doHandle(EventModel model);

    // 返回自己支持的EventType
    List<EventType> getSupportEventTypes();
}
