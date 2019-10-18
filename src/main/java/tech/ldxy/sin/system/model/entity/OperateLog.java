package tech.ldxy.sin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import tech.ldxy.sin.framework.model.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 功能描述: 
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_operate_log")
public class OperateLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 操作轨迹编号
     */
    private String trailId;

    /**
     * 操作说明
     */
    private String content;

    /**
     * 接口地址
     */
    private String uri;

    /**
     * 提交参数
     */
    private String parameters;

    /**
     * 后端方法
     */
    private String method;

    /**
     * 操作结果: 0成功, 1失败
     */
    private Integer operateResult;

    /**
     * 操作异常
     */
    private String operateException;

    /**
     * 异常消息
     */
    private String errorMessage;

    /**
     * 操作人用户名
     */
    private String username;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
