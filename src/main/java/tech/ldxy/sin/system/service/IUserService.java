package tech.ldxy.sin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.system.model.entity.User;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public interface IUserService extends IService<User>, BusinessExceptionAware {

}
