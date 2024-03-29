package com.adrianyin.rhythmshow.result;

public enum ResultCode {

    // 成功
    SUCCESS(200),
    // 失败
    FAIL(400),
    // 未认证
    UNAUTHORIZED(401),
    // 不存在
    NOT_FOUND(404),
    // 服务器错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
