package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.UserService;
import com.adrianyin.rhythmshow.utils.MailUtils;
import com.adrianyin.rhythmshow.utils.VerifyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegisterController {

    final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    // 注册
    @PostMapping(value = "/api/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> requestBody,
                           HttpServletRequest request) {

        String email = requestBody.get("email");
        String verifyCode = requestBody.get("verifyCode");
        String password = requestBody.get("password");

        // 校验验证码
        if (!VerifyCode.verifyRequestCode(request, verifyCode)) {
            return ResultFactory.buildFailResult("验证码错误！");
        }

        // 检验邮箱是否已经注册
        if (userService.emailHasBeenRegistered(email)) {
            return ResultFactory.buildFailResult("邮箱已经被注册！");
        }

        // 注册邮箱
        userService.addNormalUserWithEmailAndPassword(email, password);

        return ResultFactory.buildSuccessResult("注册成功！");
    }

    // 发送验证码
    @PostMapping(value = "/api/verify")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> requestBody,
                           HttpServletRequest request, HttpServletResponse response) {

        String email = requestBody.get("email");

        // 在session中存储验证码
        String verifyCode = VerifyCode.createVerifyCodeWithNum(6);
        String uuid = UUID.randomUUID().toString();
        request.getSession().setAttribute(uuid, verifyCode);

        // 将随机产生的uuid回写到cookie以进行验证
        Cookie cookie = new Cookie("verifyCode", uuid);
        response.addCookie(cookie);

        // 发送验证码邮件
        MailUtils.sendVerifyCode(email, verifyCode);

        return ResultFactory.buildSuccessResult("验证码已发送");
    }

}
