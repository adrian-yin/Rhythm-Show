package com.adrianyin.rhythmshow.utils;

public class VerifyCodeTest {

    public static void main(String[] args) {
        String verifyCode = VerifyCode.createVerifyCodeWithNum(6);
        System.out.println(verifyCode);
    }
}
