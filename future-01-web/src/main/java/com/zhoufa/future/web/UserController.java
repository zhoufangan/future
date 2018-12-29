package com.zhoufa.future.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhoufangan !
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 注册登录
     *
     * @param username 用户名
     * @param password 密码
     * @param email    邮箱
     * @param nickname 昵称
     * @return 登录信息
     */
    @RequestMapping("/signUp")
    public String signUpAndIn(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String nickname,
            @RequestParam String password) {
        // 1.生成密码
        String salt = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        // md5是单向加密的，不可逆，可以加密，不能解密；md5hex是可逆的，可以加密，可以解密
        String serverPassword = DigestUtils.md5Hex(DigestUtils.md5Hex(password) + salt);
        System.out.println("salt=" + salt);
        System.out.println("serverPassword=" + serverPassword);

        // 2.持久化用户信息
        User user = new User();
        user.setUsername(username);
        user.setPassword(serverPassword);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setSalt(salt);

        // 3.生成token
        Map<String, String> param = new HashMap<>(4);
        param.put("userId", 1 + "");
        param.put("validTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        param.put("UUID", UUID.randomUUID().toString().toLowerCase());
        String token = DigestUtils.md5Hex(JSON.toJSONString(param));

        System.out.println("token=" + token);

        // 4.将token放到服务器端session中，并返回给前端

        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);

        ResponseModel response = new ResponseModel();
        response.setCode(0);
        response.setMessage("success");
        response.setData(map);

        String json = JSON.toJSONString(response);
        System.out.println(json);
        return json;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回登录信息
     */
    public ResponseModel signIn(String username, String password) {


        return null;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public ResponseModel get() {

        return null;
    }
}
