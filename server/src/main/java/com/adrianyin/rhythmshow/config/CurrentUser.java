package com.adrianyin.rhythmshow.config;

import java.lang.annotation.*;

// 当前用户入参注解
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
