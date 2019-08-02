package tech.ldxy.sin.core.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import tech.ldxy.sin.core.model.Convert;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Data
public class BaseEntity extends Convert {

    private static final long serialVersionUID = 1368974545982624643L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    protected Long id;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }

}
