package tech.ldxy.sin.system.auth;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import tech.ldxy.sin.core.enums.IEnum;

/**
 * 功能描述: 权限类型枚举
 *
 * @author hxulin
 */
public enum AuthTypeEnum implements IEnum {

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

    @EnumValue
    private final int value;

    AuthTypeEnum(final int value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public int getValue() {
        return this.value;
    }

    @JsonCreator
    public static AuthTypeEnum getEnum(int value) {
        for (AuthTypeEnum menuTypeEnum : AuthTypeEnum.values()) {
            if (menuTypeEnum.getValue() == value) {
                return menuTypeEnum;
            }
        }
        throw new RuntimeException("未知的枚举值 AuthTypeEnum: " + value);
    }

}
