package tech.ldxy.sin.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.system.auth.AuthType;
import tech.ldxy.sin.system.auth.Resources;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.model.vo.LoginInfo;
import tech.ldxy.sin.system.service.IUserService;
import tech.ldxy.sin.system.web.filter.ContextManager;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/info")
    @Resources(AuthType.LOGIN)
    public ApiResponse info() {

//        LoginInfo loginInfo = UserContext.getCurrentLoginInfo();


        return ApiResponse.successOfMessage("获取用户信息。");

    }

    @GetMapping("/save")
    public ApiResponse save(String loginName, String password) {
        User user = new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        userService.save(user);
        return ApiResponse.successOfMessage("新增用户成功。");

    }
}
