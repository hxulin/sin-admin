package tech.ldxy.sin.core.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 统一的 Response 返回对象
 *
 * @author hxulin
 */
@Data
public class ApiResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String msg;

    private T data;

    private ApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ApiResponse(int code, String msg, T data) {
        this(code, msg);
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

    public static ApiResponse<Map<String, Object>> successOfData(String key, Object value) {
        Map<String, Object> data = new HashMap<>();
        data.put(key, value);
        return new ApiResponse<>(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
    }

    public static ApiResponse<?> fail() {
        return create(Status.INTERNAL_SERVER_ERROR);
    }

    public static ApiResponse<?> failOfMessage(String msg) {
        return create(Status.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static ApiResponse<?> create(int code, String msg) {
        return new ApiResponse<>(code, msg);
    }

    public static ApiResponse<?> create(Status status) {
        return create(status.getCode(), status.getMsg());
    }

}
