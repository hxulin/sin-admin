package tech.ldxy.sin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tech.ldxy.sin.system.model.entity.Role;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    int roleCodeIsExist(@Param("roleCode") String roleCode, @Param("roleId") Long roleId);

}
