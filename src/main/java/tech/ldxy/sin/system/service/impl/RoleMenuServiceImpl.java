package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.RoleMenu;
import tech.ldxy.sin.system.mapper.RoleMenuMapper;
import tech.ldxy.sin.system.service.IRoleMenuService;
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
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
