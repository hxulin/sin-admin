package tech.ldxy.sin.system.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.ldxy.sin.core.bean.Status;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.core.web.controller.AppErrorController;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.context.UserContext;

import java.lang.reflect.Method;

/**
 * 功能描述: 接口访问权限检查
 *
 * @author hxulin
 */
@Aspect
@Service
public class ApiAuthCheck implements BusinessExceptionAware {

    @Around("@annotation(requestMapping)")
    public Object checkApiAuth(ProceedingJoinPoint pjp, RequestMapping requestMapping) throws Throwable {
        if (AppErrorController.ERROR_PATH.equals(AppContext.getRequest().getRequestURI())) {
            return pjp.proceed();  // 如果是错误页面的路径, 直接放行
        }
        checkPermission(pjp);
        return pjp.proceed();
    }

    @Around("@annotation(getMapping)")
    public Object checkApiAuth(ProceedingJoinPoint pjp, GetMapping getMapping) throws Throwable {
        checkPermission(pjp);
        return pjp.proceed();
    }

    @Around("@annotation(postMapping)")
    public Object checkApiAuth(ProceedingJoinPoint pjp, PostMapping postMapping) throws Throwable {
        checkPermission(pjp);
        return pjp.proceed();
    }

    private void checkPermission(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Resources methodResources = method.getAnnotation(Resources.class);
        if (methodResources != null) {
            checkPermission(methodResources);
        } else {
            Class<?> targetClass = pjp.getTarget().getClass();
            Resources classResources = targetClass.getAnnotation(Resources.class);
            if (classResources != null) {
                checkPermission(classResources);
            }
        }
    }

    private void checkPermission(Resources resources) {
        switch (resources.auth()) {
            case LOGIN:
                // 检查用户是否登录
                UserContext.getCurrentLoginInfo();
                break;
            case AUTH:
                // 检查用户对当前资源是否有访问权限
                if (!UserContext.hasPermission(AppContext.getRequest().getRequestURI())) {
                    throw error(Status.FORBIDDEN);
                }
                break;
        }
    }
}
