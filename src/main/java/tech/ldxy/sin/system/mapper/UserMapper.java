package tech.ldxy.sin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.ldxy.sin.system.model.entity.User;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByLoginName(String loginName);

}
