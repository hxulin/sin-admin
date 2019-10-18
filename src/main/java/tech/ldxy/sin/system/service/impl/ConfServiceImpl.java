package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.Conf;
import tech.ldxy.sin.system.mapper.ConfMapper;
import tech.ldxy.sin.system.service.IConfService;
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
public class ConfServiceImpl extends ServiceImpl<ConfMapper, Conf> implements IConfService {

}
