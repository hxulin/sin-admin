package tech.ldxy.sin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ldxy.sin.framework.exception.BusinessExceptionAware;
import tech.ldxy.sin.system.model.entity.Role;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public interface IRoleService extends IService<Role>, BusinessExceptionAware {

    /**
     * 新增角色信息
     */
    void add(Role role);

    /**
     * 更新角色信息
     */
    void edit(Role role);

}
