package com.zhoufa.future.service;

import com.zhoufa.future.entity.Role;

import java.util.List;

/**
 * @author zhoufangan. Created in 2019/4/18 17:16.
 * @version V1.0
 */
public interface RoleService {
    List<Role> listRolesByUserId(Long userId);
}
