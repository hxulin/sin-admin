package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.RoleResource;
import tech.ldxy.sin.system.mapper.RoleResourceMapper;
import tech.ldxy.sin.system.service.IRoleResourceService;
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
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {

}
