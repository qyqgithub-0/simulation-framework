package com.entity;

import lombok.*;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/6/30 8:15
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private int id;
    private String name;
    private String sex;
}