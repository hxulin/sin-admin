package tech.ldxy.sin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ldxy.sin.core.bean.Const;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.mapper.UserMapper;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.service.IUserService;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String loginName, String password, String captcha, String token) {
        String dbCaptcha = UserContext.getCaptcha(token);
        if (StringUtils.isBlank(dbCaptcha)) {
            throw error("验证码已过期，请重新登录");
        }
        // 验证码有效验证次数为1次，获取后移除
        UserContext.removeCaptcha(token);
        if (!dbCaptcha.equals(captcha)) {
            throw error("验证码错误，请重新登录");
        }
        User user = userMapper.selectByLoginName(loginName);
        if (user == null || !password.equals(user.getPassword())) {
            throw error("用户名或密码错误");
        }
        if (user.getStatus() == Const.Status.DISABLE) {
            throw error("该账号已被锁定，请联系管理员");
        }
        String loginToken = UserContext.login(user);
        if (loginToken == null) {
            throw error("Token加密错误");
        }
        return loginToken;
    }

    @Override
    public void add(User user) {
        assertLoginNameNotExist(user.getLoginName(), null);
        this.save(user);
    }

    @Override
    public void edit(User user) {
        assertLoginNameNotExist(user.getLoginName(), user.getId());
        this.updateById(user);
    }

    private void assertLoginNameNotExist(String loginName, Long uid) {
        if (userMapper.loginNameIsExist(loginName, uid) > 0) {
            throw error("用户名已存在");
        }
    }

}
