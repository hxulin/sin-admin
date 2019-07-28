package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.ldxy.sin.core.model.entity.BaseEntity;

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

    private static final long serialVersionUID = 5850498493669676680L;

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
     * 菜单类型
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
    private String sort;

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

}
