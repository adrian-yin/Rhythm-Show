package com.adrianyin.rhythmshow.config;

import java.lang.annotation.*;

// 跳过token验证注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreSecurity {
}
