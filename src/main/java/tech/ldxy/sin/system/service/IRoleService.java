package tech.ldxy.sin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.system.model.entity.Role;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public interface IRoleService extends IService<Role>, BusinessExceptionAware {

    /**
     * 保存或更新角色信息
     *
     * @param id   角色ID
     * @param code 角色编码
     * @param name 角色名称
     */
    void saveOrUpdate(String id, String code, String name);

}
