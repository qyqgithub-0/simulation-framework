package com.bean.factory.support;

import com.bean.BeanDefinition;
import com.bean.BeanFactory;
import com.bean.MutablePropertyValues;
import com.bean.PropertyValue;
import com.bean.factory.config.AutowireCapableBeanFactory;
import com.exception.extension.BeanCreationException;
import com.exception.extension.BeansException;
import com.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/1 22:11
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 这是这个类的核心方法，创建bean的实例
     * @param beanName
     * @param args
     * @return
     * @throws BeansException
     */
    @Override
    protected Object createBean(String beanName, Object[] args) throws BeansException {
        return doCreate(beanName, args);
    }

    protected Object doCreate(final String beanName, Object[] args) throws BeanCreationException {
        BeanFactory beanFactory = getBeanFactory();
        Object bean = null;
        if (beanFactory instanceof DefaultListableBeanFactory) {
            //根据beanName从aliasesDefinitionMap中寻找映射
//            getAliases()
            if (beanFactory.containsBean(beanName)) {
                bean = populateBean(((DefaultListableBeanFactory) beanFactory).getBeanDefinition(beanName));
            }
        }
        return bean;
    }

    protected Object populateBean(BeanDefinition beanDefinition) {
        Object bean = beanDefinition.getBean();
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        Method[] methods = bean.getClass().getMethods();
        Field[] fields = bean.getClass().getDeclaredFields();
        List<String> setMethodNameList = new ArrayList<String>();

        //将对应Class中的属性对应的setter方法名放入集合
        for (Field field : fields) {
            String fieldName = field.getName();
            char[] ch = fieldName.toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
            fieldName = new String(ch);
            setMethodNameList.add("set" + fieldName);
        }
//        System.out.println("list:" + setMethodNameList);

        //-----------------------------------
        PropertyValue[] props = propertyValues.getPropertyValues();
        for (PropertyValue prop : props) {
            System.out.println(prop.getName() + " " + prop.getValue());
        }

        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.contains("set")) {
                for (PropertyValue prop : props) {
                    //进行setter方法对比
                    if (StringUtils.firstToLowerCase((methodName.substring(methodName.indexOf("t") + 1))).equals(prop.getName())) {
                        try {
                            method.invoke(bean, prop.getValue());
                            break;
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
//            if (setMethodNameList.contains(method.getName())) {
//                //调用bean的setter方法
//                try {
//                    method.invoke(bean, "张三");
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    System.out.println("调用失败");
//                }
//            }
        }
        return bean;
    }
}
