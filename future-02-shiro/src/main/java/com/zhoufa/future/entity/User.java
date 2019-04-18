package com.zhoufa.future.entity;

import lombok.Data;

/**
 * @author zhoufangan. Founded on 2019/4/18 17:10.
 * @version V1.0
 */
@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private String status;
    private String userType;

}
