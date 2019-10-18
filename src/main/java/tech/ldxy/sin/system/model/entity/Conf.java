package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import tech.ldxy.sin.framework.model.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 系统核心配置
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_conf")
public class Conf extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置值
     */
    private String value;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public Conf(String name, String value, Integer status, String remark) {
        this.name = name;
        this.value = value;
        this.status = status;
        this.remark = remark;
    }

}
