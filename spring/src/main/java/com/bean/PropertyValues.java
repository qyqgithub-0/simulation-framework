package com.bean;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/4 9:31
 */
public interface PropertyValues extends Iterable<PropertyValue> {

    @Override
    default Iterator<PropertyValue> iterator() {
        return Arrays.asList(getPropertyValues()).iterator();
    }

    PropertyValue[] getPropertyValues();

    PropertyValue getPropertyValue(String propertyName);

    boolean contains(String propertyName);

    boolean isEmpty();
}
