package com.zhoufa.future.service;

import com.zhoufa.future.entity.User;

/**
 * @author zhoufangan. Created in 2019/4/18 17:11.
 * @version V1.0
 */
public interface SysUserService {
    User getByUserName(String username);

    User getByPrimaryKey(Long userId);

    void updateUserLastLoginInfo(User user);
}
