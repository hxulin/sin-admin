package tech.ldxy.sin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ldxy.sin.system.mapper.RoleMapper;
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

    @Override
    public void add(Role role) {
        assertRoleCodeNotExist(role.getCode(), null);
        this.save(role);
    }

    @Override
    public void edit(Role role) {
        assertRoleCodeNotExist(role.getCode(), role.getId());
        this.updateById(role);
    }

    // 检查角色编码是否存在
    private void assertRoleCodeNotExist(String roleCode, Long roleId) {
        if (roleMapper.roleCodeIsExist(roleCode, roleId) > 0) {
            throw error("角色编码已存在");
        }
    }
}
