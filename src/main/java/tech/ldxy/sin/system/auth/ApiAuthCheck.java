package tech.ldxy.sin.system.auth;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.ldxy.sin.framework.bean.Const;
import tech.ldxy.sin.framework.bean.Status;
import tech.ldxy.sin.framework.exception.BusinessExceptionAware;
import tech.ldxy.sin.framework.util.IpUtils;
import tech.ldxy.sin.framework.util.json.JSONUtils;
import tech.ldxy.sin.framework.web.controller.AppErrorController;
import tech.ldxy.sin.framework.web.filter.RequestWrapper;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.manager.AsyncManager;
import tech.ldxy.sin.system.manager.factory.AsyncFactory;
import tech.ldxy.sin.system.model.entity.OperateLog;
import tech.ldxy.sin.system.web.filter.ContextManager;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        return handlePoint(pjp);
    }

    @Around("@annotation(getMapping)")
    public Object checkApiAuth(ProceedingJoinPoint pjp, GetMapping getMapping) throws Throwable {
        return handlePoint(pjp);
    }

    @Around("@annotation(postMapping)")
    public Object checkApiAuth(ProceedingJoinPoint pjp, PostMapping postMapping) throws Throwable {
        return handlePoint(pjp);
    }

    // 执行方法并记录日志
    private Object handlePoint(ProceedingJoinPoint pjp) throws Throwable {
        Throwable throwable = null;
        try {
            checkPermission(pjp);
            return pjp.proceed();
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            HttpServletRequest request = AppContext.getRequest();
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            Resources resources = method.getAnnotation(Resources.class);
            String resourceName = resources != null ? resources.name() : null;
            String methodName = pjp.getTarget().getClass().getName() + ":" + method.getName();
            OperateLog operateLog = new OperateLog();
            operateLog.setContent(resourceName);
            operateLog.setUri(request.getRequestURI());
            operateLog.setMethod(methodName);
            RequestWrapper requestWrapper = (RequestWrapper) request;
            String body = IOUtils.toString(requestWrapper.getBody(), request.getCharacterEncoding());
            if (body.length() != 0 && !request.getContentType().contains("multipart/form-data")) {
                // 表示以 RequestBody 方式提交的参数
                operateLog.setParameters(Pattern.compile("\\s*|\t|\r|\n").matcher(body).replaceAll(""));
            } else {
                // 表示常规方式提交参数
                Map<String, Object> params = request.getParameterMap().entrySet().stream().collect(
                        Collectors.toMap(Map.Entry::getKey,
                                v -> v.getValue().length == 1 ? v.getValue()[0] : v.getValue(), (k1, k2) -> k1));
                String requestParams = JSONUtils.toJson(params);
                operateLog.setParameters(requestParams);
            }
            if (throwable == null) {
                operateLog.setOperateResult(Const.Status.NORMAL);
            } else {
                operateLog.setOperateResult(Const.Status.DISABLE);
                operateLog.setOperateException(throwable.getClass().getName());
                operateLog.setErrorMessage(throwable.getMessage());
            }
            operateLog.setIpAddress(IpUtils.getIpAddr(request));

            // 记录操作日志
            AsyncManager.execute(AsyncFactory.recordLog(operateLog, ContextManager.getAttribute(Constant.TOKEN_KEY)));
        }
    }

    private void checkPermission(ProceedingJoinPoint pjp) {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Resources resources = method.getAnnotation(Resources.class);
        if (resources != null) {
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

}
