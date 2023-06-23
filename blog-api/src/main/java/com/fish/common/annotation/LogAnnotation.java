package com.fish.common.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author fish
 * <p>
 * 2018年4月18日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
