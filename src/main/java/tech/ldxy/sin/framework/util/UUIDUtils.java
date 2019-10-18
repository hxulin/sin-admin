package tech.ldxy.sin.framework.util;

import java.util.UUID;

/**
 * 功能描述: UUID 工具类
 *
 * @author hxulin
 */
public final class UUIDUtils {

    private UUIDUtils() {

    }

    /**
     * 生成唯一标识码
     */
    public static String createUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取32位不带中划线的UUID
     */
    public static String create32UUID() {
        return createUUID().replace("-", "");
    }

    /**
     * 获取一个字母和数字组合的随机字符串
     *
     * @param len 字符串长度
     */
    public static String createRandomString(int len) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        return randomStr(str, len);
    }

    /**
     * 获取一个数字组成的随机字符串
     *
     * @param len 字符串长度
     */
    public static String createRandomNumber(int len) {
        String str = "0123456789";
        return randomStr(str, len);
    }

    private static String randomStr(String baseStr, int len) {
        StringBuilder sBuilder = new StringBuilder(len);
        double r;
        for (int i = 0; i < len; i++) {
            r = Math.random() * baseStr.length();
            sBuilder.append(baseStr.charAt((int) r));
        }
        return sBuilder.toString();
    }

}
