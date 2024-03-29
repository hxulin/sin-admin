package tech.ldxy.sin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.ldxy.sin.framework.bean.Const;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.mapper.UserMapper;
import tech.ldxy.sin.system.mapper.UserRoleMapper;
import tech.ldxy.sin.system.model.entity.*;
import tech.ldxy.sin.system.service.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IRoleMenuService roleMenuService;

    @Autowired
    private IMenuService menuService;

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
        // 查询用户的接口权限信息
        List<UserRole> list = userRoleService.list(
                new QueryWrapper<UserRole>().eq("uid", user.getId()));
        List<Long> roleIds = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleResource> roleResourceList = roleResourceService.list(
                new QueryWrapper<RoleResource>().in("role_id", roleIds));
        Set<Long> resourceId = roleResourceList.stream().map(RoleResource::getResourceId).collect(Collectors.toSet());
        List<String> resourceMapping = resourceService.list(new QueryWrapper<Resource>().in("id", resourceId))
                .stream().map(Resource::getMapping).collect(Collectors.toList());
        // 缓存用户的接口权限信息列表
        UserContext.cachePermissionList(user.getId(), resourceMapping);

        // 查询用户的菜单权限信息
        List<RoleMenu> roleMenuList = roleMenuService.list(
                new QueryWrapper<RoleMenu>().in("role_id", roleIds));
        Set<Long> menuIds = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
        List<String> menuList = menuIds.size() > 0
                ? menuService.list(new QueryWrapper<Menu>().in("id", menuIds)).stream().map(Menu::getPath).collect(Collectors.toList())
                : Collections.EMPTY_LIST;
        // 缓存用户的菜单权限信息列表
        UserContext.cacheMenuList(user.getId(), menuList);
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

    @Override
    public void saveUserRoles(Long uid, List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            userRoleMapper.delete(Wrappers.query(new UserRole()).eq("uid", uid));
            userRoleService.saveBatch(roleIds.stream().map(e -> new UserRole(uid, e)).collect(Collectors.toList()));
        }
    }

    private void assertLoginNameNotExist(String loginName, Long uid) {
        if (userMapper.loginNameIsExist(loginName, uid) > 0) {
            throw error("用户名已存在");
        }
    }

}
