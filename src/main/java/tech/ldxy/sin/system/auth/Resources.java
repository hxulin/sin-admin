package tech.ldxy.sin.system.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述: 该注解贴在 Controller 类或 Controller 类的方法上, 用于标识权限校验
 *
 * @author hxulin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Resources {
    AuthType auth() default AuthType.OPEN;
    String name() default "";
}
