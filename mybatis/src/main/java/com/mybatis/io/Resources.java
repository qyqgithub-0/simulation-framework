package com.mybatis.io;

import java.io.InputStream;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 7:59
 */
public class Resources {

    public static InputStream getResourceAsStream(String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }
}
