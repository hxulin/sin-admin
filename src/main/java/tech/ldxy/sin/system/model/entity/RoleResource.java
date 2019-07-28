package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.ldxy.sin.core.model.entity.BaseEntity;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_resource")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = -394665207030189517L;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源ID
     */
    private Long resourceId;

}