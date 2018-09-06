package com.minmin.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author corn
 * @version 1.0.0
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
