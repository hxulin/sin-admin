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
@TableName("sys_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 4912405976605965468L;

    public UserRole(Long uid, Long roleId) {
        this.uid = uid;
        this.roleId = roleId;
    }

    /**
     * 用户编号
     */
    private Long uid;

    /**
     * 角色编号
     */
    private Long roleId;

}
