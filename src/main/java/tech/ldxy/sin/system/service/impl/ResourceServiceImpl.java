package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.Resource;
import tech.ldxy.sin.system.mapper.ResourceMapper;
import tech.ldxy.sin.system.service.IResourceService;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
