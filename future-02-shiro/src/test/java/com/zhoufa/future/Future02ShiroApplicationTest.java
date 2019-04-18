package com.zhoufa.future;

import com.zhoufa.future.entity.Resources;
import com.zhoufa.future.entity.Role;
import com.zhoufa.future.entity.User;
import com.zhoufa.future.enums.UserTypeEnum;
import com.zhoufa.future.service.ResourcesService;
import com.zhoufa.future.service.RoleService;
import com.zhoufa.future.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple Future02TestingWorkApplication.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Future02ShiroApplicationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourcesService resourcesService;

    private Long times;

    @Test
    public void test1() {
        System.out.println("test1");
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //2. 得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zs", "123");
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
        }

        System.out.println(subject.isAuthenticated());

        //6、退出
        subject.logout();
    }

    @Test
    public void test2() {
        // Shiro-密码输入错误的状态下重试次数的匹配管理
        CredentialsMatcher credentialsMatcher = new CredentialsMatcher() {
            @Override
            public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
                times++;
                return true;
            }
        };

        // Shiro-密码输入错误的状态下重试次数的匹配管理
        AuthorizingRealm realm = new AuthorizingRealm() {
            /*
             * 提供账户信息返回认证信息（用户的角色信息集合）
             */
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
                // 获取用户的输入的账号.
                String username = (String) token.getPrincipal();
                User user = userService.getByUserName(username);
                if (user == null) {
                    throw new UnknownAccountException("账号不存在！");
                }
                if (user.getStatus() != null && "1".equals(user.getStatus())) {
                    throw new LockedAccountException("帐号已被锁定，禁止登录！");
                }

                // principal参数使用用户Id，方便动态刷新用户权限
                return new SimpleAuthenticationInfo(
                        user.getId(),
                        user.getPassword(),
                        ByteSource.Util.bytes(username),
                        getName()
                );
            }

            /*
             * 权限认证，为当前登录的Subject授予角色和权限（角色的权限信息集合）
             */
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
                // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

                Long userId = (Long) SecurityUtils.getSubject().getPrincipal();

                // 赋予角色
                List<Role> roleList = roleService.listRolesByUserId(userId);
                for (Role role : roleList) {
                    info.addRole(role.getName());
                }

                // 赋予权限
                List<Resources> resourcesList;
                User user = userService.getByPrimaryKey(userId);
                if (null == user) {
                    return info;
                }
                // ROOT用户默认拥有所有权限
                if (UserTypeEnum.ROOT.toString().equalsIgnoreCase(user.getUserType())) {
                    resourcesList = resourcesService.listAll();
                } else {
                    resourcesList = resourcesService.listByUserId(userId);
                }

                if (!CollectionUtils.isEmpty(resourcesList)) {
                    Set<String> permissionSet = new HashSet<>();
                    for (Resources resources : resourcesList) {
                        String permission = null;
                        if (!StringUtils.isEmpty(permission = resources.getPermission())) {
                            permissionSet.addAll(Arrays.asList(permission.trim().split(",")));
                        }
                    }
                    info.setStringPermissions(permissionSet);
                }
                return info;
            }
        };
        realm.setCredentialsMatcher(credentialsMatcher);

        // 设置缓存
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

        // 设置session管理器
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(24 * 60 * 60 * 1000L);
        sessionManager.setSessionDAO(redisSessionDAO());

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(realm);
        // 设置缓存管理器：可以试用redis
        securityManager.setCacheManager(ehCacheManager);
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());

        SecurityUtils.setSecurityManager(securityManager);
    }
}
