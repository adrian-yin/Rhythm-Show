package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.config.IgnoreSecurity;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.UserService;
import com.adrianyin.rhythmshow.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @IgnoreSecurity
    @PostMapping(value = "/api/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> requestBody) {

        String email = requestBody.get("email");
        String password = requestBody.get("password");

        User user = userService.getByEmailAndPassword(email, password);
        if (user != null) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("role", Integer.toString(user.getRole()));
            data.put("token", JwtUtils.sign(user));
            return ResultFactory.buildSuccessResult(data);
        } else {
            return ResultFactory.buildFailResult("邮箱或密码错误！");
        }
    }
}
