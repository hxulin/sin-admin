package tech.ldxy.sin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.mapper.RoleMapper;
import tech.ldxy.sin.system.mapper.UserMapper;
import tech.ldxy.sin.system.model.entity.Role;
import tech.ldxy.sin.system.service.IRoleService;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    public void saveOrUpdate(String id, String code, String name) {
        Role role = new Role();
        if (StringUtils.isNotEmpty(id)) {
            role.setId(Long.valueOf(id));
        }
        role.setCode(code);
        role.setName(name);
        Long uid = UserContext.getCurrentLoginInfo().getId();
        role.setCreateUid(uid);
        role.setUpdateUid(uid);
        this.saveOrUpdate(role);

    }
}
