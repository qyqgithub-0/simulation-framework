package com.using.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/7/7 8:25
 */
@Data
public class User implements Serializable {

    private Integer id;
    private String nick;
    private String phone;
    private String email;
}
