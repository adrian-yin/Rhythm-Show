package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.config.CurrentUser;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {

    final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/getuser")
    @ResponseBody
    public Result getUser(@RequestParam(name = "userId") int userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return ResultFactory.buildSuccessResult(user);
        } else {
            return ResultFactory.buildFailResult("未知用户");
        }
    }

    @GetMapping("/api/currentuser")
    @ResponseBody
    public Result getCurrentUser(@CurrentUser User user) {
        return ResultFactory.buildSuccessResult(user);
    }
}
