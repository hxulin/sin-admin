package tech.ldxy.sin.system.service;

import java.util.List;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public interface ISchemaUpdateService {

    /**
     * 说明: 执行自定义的 SQL 内容（完成库表结构更新）
     *
     *      该方法仅提供给更新库表使用，程序自检时自动调用，开发人员请不要使用此方法
     *
     * @author hxulin
     */
    void updateDbSchema(List<String> sqlList);

    /**
     * 检测数据库中基础配置表是否存在
     */
    boolean sysConfTableIsExist();

}
