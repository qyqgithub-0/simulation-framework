package com.mybatis.parse;

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
}
