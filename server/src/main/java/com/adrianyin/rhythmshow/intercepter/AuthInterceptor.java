package com.adrianyin.rhythmshow.intercepter;

import com.adrianyin.rhythmshow.config.IgnoreSecurity;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.service.UserService;
import com.adrianyin.rhythmshow.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// token认证拦截器
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();

        // 如果有@IgnoreSecurity注解则直接通过
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("无效token");
        }

        // 根据token获取当前用户
        User user = userService.getUserByToken(token);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 验证token
        if (JwtUtils.verify(token)) {
            request.setAttribute("currentUser", user);
            return true;
        }

        return false;
    }
}
