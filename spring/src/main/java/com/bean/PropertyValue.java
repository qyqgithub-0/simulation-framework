package com.bean;

import com.util.Assert;

import java.io.Serializable;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/4 9:32
 */
public class PropertyValue implements Serializable {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        Assert.notNull(name, "Name must not be null");
        this.name = name;
        this.value = value;
    }

    public PropertyValue(PropertyValue original) {
        Assert.notNull(original, "Original must not be null");
        this.name = original.getName();
        this.value = original.getValue();
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
