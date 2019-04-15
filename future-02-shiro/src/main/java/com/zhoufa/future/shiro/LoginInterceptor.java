package com.zhoufa.future.shiro;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户认证拦截
 *
 * @author zhoufangan. Founded on 2019/4/15 16:54.
 * @version V1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //得到请求的url
        String url = request.getRequestURI();
        //判断是否是公开 地址
        //实际开发中需要公开 地址配置在配置文件中
        //从配置中取逆名访问url
        List<String> open_urls = ResourcesUtil.gekeyList("anonymousURL");
        //遍历公开 地址，如果是公开 地址则放行
        for(String open_url:open_urls){
            if(url.indexOf(open_url)>=0){
                //如果是公开 地址则放行
                return true;
            }
        }
        //判断用户身份在session中是否存在
        HttpSession session = request.getSession();
        ActiveUser activeUser = (ActiveUser) session.getAttribute("activeUser");
        //如果用户身份在session中存在放行
        if(activeUser!=null){
            return true;
        }
        //执行到这里拦截，跳转到登陆页面，用户进行身份认证
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);

        //如果返回false表示拦截不继续执行handler，如果返回true表示放行
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
