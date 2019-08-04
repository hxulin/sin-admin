package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.UserRole;
import tech.ldxy.sin.system.mapper.UserRoleMapper;
import tech.ldxy.sin.system.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
