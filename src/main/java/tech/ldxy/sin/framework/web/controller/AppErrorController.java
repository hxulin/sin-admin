package tech.ldxy.sin.framework.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.framework.bean.Status;
import tech.ldxy.sin.framework.exception.BusinessException;
import tech.ldxy.sin.framework.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 功能描述: 全局错误处理控制器
 *
 * @author hxulin
 */
@RestController
public class AppErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(ERROR_PATH)
    // @ResponseStatus(HttpStatus.OK)
    public ApiResponse errorApiHandler(HttpServletRequest request, HttpServletResponse response) {
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == 400 || status == 404 || status == 405) {
            return ApiResponse.create(Status.getByCode(status));
        }
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (throwable != null) {

            while(throwable.getCause() != null) {
                throwable = throwable.getCause();
            }

            String stackTrace = getStackTrace(throwable);
            System.err.println(throwable.getClass().getName());


            if (throwable instanceof BusinessException) {
                // 业务异常, 记录一般性日志
                BusinessException ex = (BusinessException) throwable;
                if (ex.getCode() == 401 || ex.getCode() == 403) {
                    response.setStatus(ex.getCode());
                }

                System.err.println(stackTrace);
                return ApiResponse.create(ex.getCode(), ex.getMsg());
            } else if (throwable instanceof ConstraintViolationException) {
                // 请求参数校验异常
                response.setStatus(Status.NOT_VALID_PARAM.getCode());
                return ApiResponse.create(Status.NOT_VALID_PARAM);
            } else {
                // 未知类型异常, 记录严重性日志, 发送邮件提醒
                System.err.println("未知类型异常, 记录日志, 发送邮件提醒");
                System.err.println(stackTrace);
            }
        }
        return ApiResponse.fail();
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
