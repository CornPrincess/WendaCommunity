package com.minmin.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.minmin.wenda.util.JedisAdapter;
import com.minmin.wenda.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-04
 * Time: 00:02
 */
@Service
public class EventProducer {
    @Autowired
    private JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel eventModel) {
        try {
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
