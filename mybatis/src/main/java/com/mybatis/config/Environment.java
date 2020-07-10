package com.mybatis.config;

import lombok.Data;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:22
 */
@Data
public class Environment {

    private String driver;
    private String url;
    private String username;
    private String password;
}
