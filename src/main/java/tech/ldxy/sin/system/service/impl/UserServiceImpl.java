package tech.ldxy.sin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /*@Autowired
    private UserMapper userMapper;

    public void save(User user) {
        userMapper.insert(user);
    }*/

}
