package com.cs.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;




@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthValidate {
	
	/** 登录类方法，无需过滤即可访问 */
    boolean exceptAuth() default false;
	
    /** 无需验证用户资源权限即可访问 */
	boolean exceptResource() default false;
}
