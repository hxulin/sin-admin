package tech.ldxy.sin.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 功能描述: 库表变更
 *
 * @author hxulin
 */
@Mapper
public interface SchemaUpdateMapper {

    /**
     * 说明: 执行自定义的 SQL 内容（完成库表结构更新）
     *
     *      该方法仅提供给更新库表使用，程序自检时自动调用，开发人员请不要使用此方法
     *
     * @author hxulin
     */
    List<LinkedHashMap<String, Object>> executeSql(String sql);

}
