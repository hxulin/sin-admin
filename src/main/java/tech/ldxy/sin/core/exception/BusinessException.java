package tech.ldxy.sin.core.exception;

/**
 * 功能描述: 业务异常
 *
 * @author hxulin
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 954782654855622995L;

    private int code;
    private String msg;

    BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
