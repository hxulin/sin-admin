package tech.ldxy.sin.system.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述: 该注解贴在不需要校验权限的 Controller 类或 Controller 类的方法上
 *
 * @author hxulin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenResource {
}
