package tech.ldxy.sin.system.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.ldxy.sin.system.model.entity.User;

import java.util.Date;

/**
 * 功能描述: 用户登录信息
 *
 * @author hxulin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginInfo extends User {

    private static final long serialVersionUID = 2845415606194265336L;

    private String loginIp;

    private Date loginTime;

    private String loginLocation;

}
