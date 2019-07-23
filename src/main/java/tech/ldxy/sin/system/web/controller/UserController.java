package tech.ldxy.sin.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.service.IUserService;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/save")
    public ApiResponse save(String loginName, String password) {
        User user = new User(loginName, password);
        userService.save(user);
        return ApiResponse.successOfMessage("新增用户成功。");

    }
}
