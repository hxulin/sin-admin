package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.ldxy.sin.framework.model.entity.BaseEntity;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu_resource")
public class MenuResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 资源ID
     */
    private Long resourceId;

}
