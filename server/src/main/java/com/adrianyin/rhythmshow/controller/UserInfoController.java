package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.config.CurrentUser;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.CollectService;
import com.adrianyin.rhythmshow.service.ShareService;
import com.adrianyin.rhythmshow.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class UserInfoController {

    final UserService userService;
    final ShareService shareService;
    final CollectService collectService;

    public UserInfoController(UserService userService,
                              ShareService shareService,
                              CollectService collectService) {
        this.userService = userService;
        this.shareService = shareService;
        this.collectService = collectService;
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

    @PostMapping("/api/changeuserinfo")
    @ResponseBody
    public Result changeUserInfo(@RequestBody User userInfo) {
        User user = userService.getById(userInfo.getId());
        user.setNickname(userInfo.getNickname());
        user.setNote(userInfo.getNote());
        user.setBirthday(userInfo.getBirthday());
        user.setGender(userInfo.getGender());

        userService.changeUserInfo(user);
        return ResultFactory.buildSuccessResult("修改成功");
    }

    @PostMapping("/api/changepassword")
    @ResponseBody
    public Result changePassword(@RequestBody Map<String, String> requestBody) {
        int userId = Integer.parseInt(requestBody.get("userId"));
        User user = userService.getById(userId);
        String originalPassword = requestBody.get("originalPassword");
        String newPassword = requestBody.get("newPassword");

        if (userService.checkPassword(user, originalPassword)) {
            userService.changePassword(user, newPassword);
            return ResultFactory.buildSuccessResult("修改成功");
        } else {
            return ResultFactory.buildFailResult("原密码错误！");
        }
    }

    @GetMapping("/api/getcollects")
    @ResponseBody
    public Result getCollects(@RequestParam(name = "userId") int userId) {
        User user = userService.getById(userId);
        List<Share> collects = collectService.getShareByUser(user);
        return ResultFactory.buildSuccessResult(collects);
    }

    @GetMapping("/api/getworks")
    @ResponseBody
    public Result getWorks(@RequestParam(name = "userId") int userId) {
        User user = userService.getById(userId);
        List<Share> works = shareService.getByUser(user);
        return ResultFactory.buildSuccessResult(works);
    }
}
