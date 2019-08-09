package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.Menu;
import tech.ldxy.sin.system.mapper.MenuMapper;
import tech.ldxy.sin.system.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
