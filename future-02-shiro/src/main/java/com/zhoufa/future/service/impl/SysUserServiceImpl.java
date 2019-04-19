package com.zhoufa.future.service.impl;

import com.zhoufa.future.entity.User;
import com.zhoufa.future.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author zhoufangan. Founded on 2019/4/18 17:12.
 * @version V1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Override
    public User getByUserName(String username) {
        return null;
    }

    @Override
    public User getByPrimaryKey(Long userId) {
        return null;
    }

    @Override
    public void updateUserLastLoginInfo(User user) {

    }
}
