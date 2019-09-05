package com.simple.entiy;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author songabao
 * @date 2019/8/27 17:04
 */
@Data
public class User implements Serializable{
    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private Date createDate;
}
