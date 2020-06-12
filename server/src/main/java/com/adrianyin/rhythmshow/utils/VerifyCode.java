package com.adrianyin.rhythmshow.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

// 提供验证码生成方法
public class VerifyCode {

    // 生成指定长度的纯数字验证码
    public static String createVerifyCodeWithNum(int length) {

        StringBuilder verifyCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            verifyCode.append(random.nextInt(10));
        }

        return verifyCode.toString();
    }

    // 验证码校验
    public static boolean verifyRequestCode(HttpServletRequest request, String requestVerifyCode) {

        String verifyCode = null;
        String uuid = null;
        Cookie[] cookies = request.getCookies();

        // 取出cookie中的uuid
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("verifyCode")) {
                uuid = cookies[i].getValue();
                break;
            }
        }
        if (uuid == null) {
            return false;
        }

        // 根据uuid取出session中的验证码进行比对
        verifyCode = (String) request.getSession().getAttribute(uuid);
        if (verifyCode == null) {
            return false;
        } else if (requestVerifyCode.toLowerCase().equals(verifyCode.toLowerCase())) {
            // 删除session中的验证码
            request.getSession().getServletContext().removeAttribute(uuid);
            return true;
        } else {
            return false;
        }
    }
}
