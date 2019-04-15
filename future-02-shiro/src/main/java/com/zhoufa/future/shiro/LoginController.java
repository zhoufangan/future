package com.zhoufa.future.shiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author zhoufangan. Founded on 2019/4/15 19:07.
 * @version V1.0
 */
@Controller
public class LoginController {
    @Autowired
    private SysService sysService;

    @RequestMapping("/login")
    public String login(HttpSession session, String randomcode, String usercode, String password) throws Exception {
        //校验验证码，防止恶性攻击
        //从session获取正确验证码
        String validateCode = (String) session.getAttribute("validateCode");

        //输入的验证和session中的验证进行对比
        if (!randomcode.equals(validateCode)) {
            //抛出异常
            throw new RuntimeException("验证码输入错误");
        }

        //调用service校验用户账号和密码的正确性
        ActiveUser activeUser = sysService.authenticat(usercode, password);

        //如果service校验通过，将用户身份记录到session
        session.setAttribute("activeUser", activeUser);
        //重定向到商品查询页面
        return "redirect:/first.action";
    }

}
