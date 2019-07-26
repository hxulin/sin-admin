package tech.ldxy.sin.system.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tech.ldxy.sin.core.model.entity.BaseEntity;

import java.util.Date;

/**
 * 功能描述: 用户登录信息
 *
 * @author hxulin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginInfo extends BaseEntity {

    private static final long serialVersionUID = -5252587862968558687L;

    private String loginName;

    private String nickname;

    private String loginIp;

    private Date loginTime;

    private String loginLocation;

}
