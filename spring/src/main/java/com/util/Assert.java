package com.util;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/29 16:48
 */
public class Assert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
