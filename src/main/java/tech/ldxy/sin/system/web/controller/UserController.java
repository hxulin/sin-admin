package tech.ldxy.sin.system.web.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.system.auth.AuthType;
import tech.ldxy.sin.system.auth.Resources;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.service.IUserService;

import javax.validation.constraints.Pattern;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController implements BusinessExceptionAware {

    @Autowired
    private IUserService userService;

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public ApiResponse add(@RequestParam @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$") String loginName,
                           @RequestParam @Length(min = 6, max = 16) String password,
                           @Length(max = 20)String nickname) {
        User user = new User(loginName, UserContext.encryptPwd(password), nickname);
        userService.add(user);
        return ApiResponse.successOfMessage("新增用户成功。");
    }

    /**
     * 修改用户
     */
    @PostMapping("/edit")
    @Resources(name = "修改用户信息", auth = AuthType.AUTH)
    public ApiResponse save(String loginName, String password) {
        /*User user = new User();
        user.setLoginName(loginName);
        user.setPassword(password);
        userService.save(user);*/
        return ApiResponse.successOfMessage("新增用户成功。");

    }

    @GetMapping("/info")
    @Resources(name = "获取用户信息", auth = AuthType.LOGIN)
    public ApiResponse info() {

//        LoginInfo loginInfo = UserContext.getCurrentLoginInfo();

        return ApiResponse.successOfMessage("获取用户信息。");

    }


}
