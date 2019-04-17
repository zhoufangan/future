package com.zhoufa.future.shiro;

import com.zhoufa.future.custom.MD5Util;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhoufangan. Founded on 2019/4/15 19:08.
 * @version V1.0
 */
@Service
public class SysService {

    public ActiveUser authenticat(String userCode, String password) {
        /*
         认证过程：
         根据用户身份（账号）查询数据库，如果查询不到用户不存在
         对输入的密码 和数据库密码 进行比对，如果一致，认证通过
         */
        //根据用户账号查询数据库
        SysUser sysUser = this.findSysUserByUserCode(userCode);

        if (sysUser == null) {
            //抛出异常
            throw new RuntimeException("用户账号不存在");
        }

        //数据库密码 (md5密码 )
        String password_db = sysUser.getPassword();

        //对输入的密码 和数据库密码 进行比对，如果一致，认证通过
        //对页面输入的密码 进行md5加密
        String password_input_md5 = MD5Util.encrypByMd5(password);
        if (!password_input_md5.equalsIgnoreCase(password_db)) {
            //抛出异常
            throw new RuntimeException("用户名或密码 错误");
        }
        //得到用户id
        String userid = sysUser.getId() + "";
        //根据用户id查询菜单
        List<SysPermission> menus = this.findMenuListByUserId(userid);

        //根据用户id查询权限url
        List<SysPermission> permissions = this.findPermissionListByUserId(userid);

        //认证通过，返回用户身份信息
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserid(sysUser.getId());
        activeUser.setUsercode(userCode);
        activeUser.setUsername(sysUser.getUsername());//用户名称

        //放入权限范围的菜单和url
        activeUser.setMenus(menus);
        activeUser.setPermissions(permissions);

        return activeUser;
    }

    private List<SysPermission> findPermissionListByUserId(String userid) {
        return null;
    }

    private List<SysPermission> findMenuListByUserId(String userid) {
        return null;
    }

    /**
     * 这是查数据库操作
     *
     * @param userCode 用户编码
     * @return 系统用户
     */
    private SysUser findSysUserByUserCode(String userCode) {
        SysUser user = new SysUser();
        user.setId(1 + "");
        user.setUserCode(userCode);
        user.setUsername("root");
        user.setPassword("123456");
        return user;
    }


}
