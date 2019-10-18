package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
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
@TableName("sys_resource")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 映射路径
     */
    private String mapping;

    /**
     * 权限认证类型
     */
    private Integer authType;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Resource other = (Resource) obj;
        return mapping == null ? other.mapping == null : mapping.equals(other.mapping);
    }

    @Override
    public int hashCode() {
        return 31 + (mapping == null ? 0 : mapping.hashCode());
    }

}
