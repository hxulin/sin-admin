package tech.ldxy.sin.core.exception;

import tech.ldxy.sin.core.bean.Status;

/**
 * 功能描述: 异常增强接口
 *
 * @author hxulin
 */
public interface BusinessExceptionAware {

    default BusinessException error(String msg) {
        return error(Status.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    default BusinessException error(Status status) {
        return error(status.getCode(), status.getMsg());
    }

    default BusinessException error(int code, String msg) {
        return new BusinessException(code, msg);
    }
}
