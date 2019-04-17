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

    /**
     * 用户id（主键）
     */
    private String userid;
    /**
     * 用户账号
     */
    private String usercode;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 菜单
     */
    private List<SysPermission> menus;
    /**
     * 权限
     */
    private List<SysPermission> permissions;

}
