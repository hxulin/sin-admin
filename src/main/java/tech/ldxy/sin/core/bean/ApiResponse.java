package tech.ldxy.sin.core.bean;

import java.io.Serializable;

/**
 * 功能描述: 统一的 Response 返回对象
 *
 * @author hxulin
 */
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 196497372528417899L;

    private int code;

    private String msg;

    private T data;

    public ApiResponse() {

    }

    private ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ApiResponse(int code, String msg, T data) {
        this(code, msg);
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static ApiResponse<?> success() {
        return create(Status.SUCCESS);
    }
    
    public static ApiResponse<?> successOfMessage(String msg) {
    	return create(Status.SUCCESS.getCode(), msg);
    }

    public static <T> ApiResponse<T> successOfData(T data) {
        return new ApiResponse<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
    }

    public static ApiResponse<?> fail() {
        return create(Status.INTERNAL_SERVER_ERROR);
    }

    public static ApiResponse<?> failOfMessage(String msg) {
        return create(Status.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static ApiResponse<?> notValidParam() {
        return create(Status.NOT_VALID_PARAM);
    }

    public static ApiResponse<?> forbidden() {
        return create(Status.FORBIDDEN);
    }

    public static ApiResponse<?> notFound() {
        return create(Status.NOT_FOUND);
    }

    public static ApiResponse<?> notLogin() {
        return create(Status.NOT_LOGIN);
    }

    public static ApiResponse<?> create(int code, String msg) {
        return new ApiResponse<>(code, msg);
    }

    private static ApiResponse<?> create(Status status) {
        return create(status.getCode(), status.getMsg());
    }

}
