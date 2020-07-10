package com.mybatis.parse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 16:11
 */
public class GenericTokenParser {

    /**
     * 将读取的sql语句的#{}替换为?
     * @param text 源文本
     * @return 可以用于预处理的文本
     */
    public static String parse(String text) {
        return text.replaceAll("#[^}]*}", "?");
    }

    /**
     * 解析SQL语句，将{}中间的内容读取放入集合并返回
     * @param text 源文本
     * @return 大括号值的集合
     */
    public static List<String> parseSQLGetString(String text) {
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile("\\{(.*?)}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group(1));
        }
        return list;
    }

    /**
     * 将原始的sql语句（包含#{}的）参数替换为obj里面对应的字段属性，返回真正可执行的sql语句
     * @param original 原始sql语句
     * @param obj POJO对象
     * @return 可执行的sql语句
     */
    public static String parseSQLStatement(String original, Object obj) throws IllegalAccessException {
        List<String> list = parseSQLGetString(original);
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder(original);
        for (String item : list) {
            for (Field field : fields) {
                if (item.equals(field.getName())) {
                    field.setAccessible(true);
                    if (field.getType() == String.class) {
                        sb.replace(0, sb.capacity(), sb.toString().replace("#{" + item + "}", "'" + field.get(obj) + "'"));
                    } else {
                        sb.replace(0, sb.capacity(), sb.toString().replace("#{" + item + "}", field.get(obj) + ""));
                    }
                    break;
                }
            }
        }
        return sb.toString();
    }
}
