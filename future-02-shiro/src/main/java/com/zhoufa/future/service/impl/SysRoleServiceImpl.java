package com.zhoufa.future.service.impl;

import com.zhoufa.future.entity.Role;
import com.zhoufa.future.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhoufangan. Founded on 2019/4/18 17:17.
 * @version V1.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Override
    public List<Role> listRolesByUserId(Long userId) {
        return null;
    }
}
