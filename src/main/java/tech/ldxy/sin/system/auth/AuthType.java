package tech.ldxy.sin.system.auth;

/**
 * 功能描述: 权限类型枚举
 *
 * @author hxulin
 */
public enum AuthType {

    /**
     * 开放,无需鉴权
     */
    OPEN(1),
    /**
     * 需要登录
     */
    LOGIN(2),
    /**
     * 需要鉴定是否具有权限
     */
    AUTH(3);

    private int value;

    AuthType(int value) {
        this.value = value;
    }


    public static AuthType getEnum(int value) {
        for (AuthType authType : AuthType.values()) {
            if (authType.value == value) {
                return authType;
            }
        }
        throw new RuntimeException("未知的枚举值 AuthType: " + value);
    }

}
