package tech.ldxy.sin.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import tech.ldxy.sin.system.context.UserContext;

import java.util.Date;

/**
 * 功能描述: MyBatisPlus 自动填充器
 *
 * @author hxulin
 */
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 创建时间
     */
    private final String createTime = "createTime";
    /**
     * 修改时间
     */
    private final String updateTime = "updateTime";
    /**
     * 创建者ID
     */
    private final String createUid = "createUid";

    /**
     * 修改者ID
     */
    private final String updateUid = "updateUid";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        setInsertFieldValByName(createTime, date, metaObject);
        setInsertFieldValByName(createUid, currentUid(), metaObject);
        setInsertFieldValByName(updateTime, date, metaObject);
        setInsertFieldValByName(updateUid, currentUid(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setUpdateFieldValByName(updateTime, new Date(), metaObject);
        setUpdateFieldValByName(updateUid, currentUid(), metaObject);
    }

    /**
     * 获取当前用户ID
     */
    private Long currentUid() {
        Long uid = null;
        try {
            uid = UserContext.getCurrentLoginInfo().getId();
        } catch (Exception ignored) {
        }
        return uid;
    }
}
