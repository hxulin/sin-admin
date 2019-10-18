package tech.ldxy.sin.system.service.impl;

import tech.ldxy.sin.system.model.entity.OperateLog;
import tech.ldxy.sin.system.mapper.OperateLogMapper;
import tech.ldxy.sin.system.service.IOperateLogService;
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
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
