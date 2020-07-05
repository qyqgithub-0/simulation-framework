package com.bean;

import java.io.Serializable;
import java.util.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/4 9:38
 */
public class MutablePropertyValues implements PropertyValues, Serializable {

    private final List<PropertyValue> propertyValueList;
    private Set<String> processedProperties;

    public MutablePropertyValues() {
        this.propertyValueList = new ArrayList<>(0);
    }

    /**
     * 从map中构造一个MutablePropertyValues
     * @param original 一个包含了属性名和属性值的map
     */
    public MutablePropertyValues(Map<?, ?> original) {
        if (original != null) {
            this.propertyValueList = new ArrayList<>(original.size());
            original.forEach((attrName, attrValue) -> this.propertyValueList.add(
                    new PropertyValue(attrName.toString(), attrValue)
            ));
        } else {
            this.propertyValueList = new ArrayList<>(0);
        }
    }

    public MutablePropertyValues(List<PropertyValue> propertyValueList) {
        this.propertyValueList = (propertyValueList != null ? propertyValueList : new ArrayList<>());
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }

    public int size() {
        return this.propertyValueList.size();
    }

    public void setPropertyValueAt(PropertyValue pv, int i) {
        this.propertyValueList.set(i, pv);
    }

    public MutablePropertyValues addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
        return this;
    }

    public MutablePropertyValues addPropertyValues(PropertyValues other) {
        if (other != null) {
            PropertyValue[] pvs = other.getPropertyValues();
            for (PropertyValue pv : pvs) {
                addPropertyValue(new PropertyValue(pv));
            }
        }
        return this;
    }

    public void addPropertyValue(String propertyName, Object propertyValue) {
        addPropertyValue(new PropertyValue(propertyName, propertyValue));
    }

    public void removePropertyValue(PropertyValue pv) {
        this.propertyValueList.remove(pv);
    }

    public void removePropertyValue(String propertyName) {
        this.propertyValueList.remove(getPropertyValue(propertyName));
    }

    @Override
    public Iterator<PropertyValue> iterator() {
        return Collections.unmodifiableCollection(this.propertyValueList).iterator();
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }

    @Override
    public boolean contains(String propertyName) {
        return (getPropertyValue(propertyName) != null ||
                (this.processedProperties != null && this.processedProperties.contains(propertyName)));
    }

    public void registerProcessedProperty(String propertyName) {
        if (this.processedProperties == null) {
            this.processedProperties = new HashSet<>(4);
        }
        this.processedProperties.add(propertyName);
    }

    public void clearProcessedProperty(String propertyName) {
        if (this.processedProperties != null) {
            this.processedProperties.remove(propertyName);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
