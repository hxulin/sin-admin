package tech.ldxy.sin.framework.exception;

import lombok.Getter;

/**
 * 功能描述: 业务异常
 *
 * @author hxulin
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
