package com.zhoufa.future.shiro;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 *
 * @author zhoufangan. Founded on 2019/4/15 16:36.
 * @version V1.0
 */
@Data
public class ActiveUser implements Serializable {

    private static final long serialVersionUID = 5028732888665237190L;

    private String userid;
    private String usercode;

    private String username;

    private List<SysPermission> menus;
    private List<SysPermission> permissions;

}
