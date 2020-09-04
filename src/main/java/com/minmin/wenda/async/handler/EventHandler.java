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
    void doHandle(EventModel model);

    // 返回自己支持的EventType
    List<EventType> getSupportEventTypes();
}
