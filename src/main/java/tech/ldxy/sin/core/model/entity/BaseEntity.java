package tech.ldxy.sin.core.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import tech.ldxy.sin.core.model.Convert;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
