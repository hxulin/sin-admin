package tech.ldxy.sin.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ldxy.sin.system.mapper.SchemaUpdateMapper;
import tech.ldxy.sin.system.service.ISchemaUpdateService;

import java.util.List;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Service
@Transactional
public class SchemaUpdateServiceImpl implements ISchemaUpdateService {

    private SchemaUpdateMapper schemaUpdateMapper;

    @Autowired
    public void setSchemaUpdateMapper(SchemaUpdateMapper schemaUpdateMapper) {
        this.schemaUpdateMapper = schemaUpdateMapper;
    }

    public void updateDbSchema(List<String> sqlList) {
        for (String sql : sqlList) {
            schemaUpdateMapper.executeSql(sql);
        }
    }

    public boolean sysConfTableIsExist() {
        String checkSql = "SELECT COUNT(t.TABLE_NAME) AS CNT FROM information_schema.TABLES t, information_schema.SCHEMATA n WHERE t.TABLE_NAME = 'sys_conf' AND n.SCHEMA_NAME = 'sin_admin'";
        String count = schemaUpdateMapper.executeSql(checkSql).get(0).get("CNT").toString();
        return Integer.valueOf(count) > 0;
    }

}
