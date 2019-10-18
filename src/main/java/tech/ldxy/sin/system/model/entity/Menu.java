package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.ldxy.sin.framework.model.entity.BaseEntity;

import java.util.Date;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父级菜单编号
     */
    private long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 菜单类型: 1目录, 2菜单, 3按钮
     */
    private Integer menuType;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 按钮标识
     */
    private String btnClass;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUid;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUid;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Menu(long parentId, String menuName, String path, Integer menuType, String icon, String btnClass, Long sort) {
        this.parentId = parentId;
        this.menuName = menuName;
        this.path = path;
        this.menuType = menuType;
        this.icon = icon;
        this.btnClass = btnClass;
        this.sort = sort;
    }
}
