package com.mybatis.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 15:58
 */
public class Reflection {

    /**
     * 将结果集映射到Java对象中
     * @param entity JavaBean
     * @param resultSet 结果集
     */
    public static void setPropertiesToBeanFromResultSet(Object entity, ResultSet resultSet) {
        //根据结果集，获取到数据库的元数据信息，里面有很详细的表的列、字段的信息
        try {
            Field[] entityFields = entity.getClass().getDeclaredFields();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //字段个数
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                //getColumnName：得到指定列的名称    getColumnClassName：得到SQL在Java中对应的字段类型的全限定类名
                String propertyName = resultSetMetaData.getColumnName(i);
                Object propertyValue = resultSet.getObject(i);          //得到列的内容，作为给JavaBean设置的参数
                //得到字段属性，得到的是一个基本数据类型的字节码对象
//                Class<?> fieldClass = Class.forName(resultSetMetaData.getColumnClassName(i));

                //我们默认认为数据库字段属性和实体类属性一样
                for (Field entityField : entityFields) {
                    if (propertyName.equals(entityField.getName())) {           //找到对应的Field
                        entityField.setAccessible(true);
                        entityField.set(entity, propertyValue);                 //对JavaBean进行赋值
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}
