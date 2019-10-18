package tech.ldxy.sin.system.common;

/**
 * 功能描述: 数据字典常量
 *
 * @author hxulin
 */
public interface Constant {

    /**
     * Token 的名称
     */
    String TOKEN_KEY = "Sin-Token";

    /**
     * 系统配置常量键值
     */
    interface Conf {

        /**
         * 记录库表更新的 SQL 版本号
         */
        String DB_SCHEMA_VERSION = "DB_SCHEMA_VERSION";

    }

}
