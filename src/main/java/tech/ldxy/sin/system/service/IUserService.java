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

    /**
     * 用户登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @param captcha   验证码
     * @param token     验证码Token
     * @return 登录成功后的加密会话Token
     */
    String login(String loginName, String password, String captcha, String token);

    /**
     * 新增角色信息
     */
    void add(User user);

    /**
     * 更新角色信息
     */
    void edit(User user);

}
