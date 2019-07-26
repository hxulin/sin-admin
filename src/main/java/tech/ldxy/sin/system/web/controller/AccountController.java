package tech.ldxy.sin.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.core.util.Captcha;
import tech.ldxy.sin.core.util.UUIDUtils;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.core.util.IpUtils;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Controller
public class AccountController implements BusinessExceptionAware {

    @Autowired
    private IUserService userService;

    @GetMapping("/static/images/securityCode.png")
    public void code(HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/png");
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        String captchaToken = UUIDUtils.createUUID();
        response.setHeader(Constant.TOKEN_KEY, captchaToken);
        Captcha captcha = new Captcha(80, 32, 4, 10);
        // 将验证码信息保存到Session中
        UserContext.setCaptcha(captchaToken, captcha.getCode());
        captcha.write(response.getOutputStream());
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse login(@RequestParam String loginName, @RequestParam String password,
                             @RequestParam String captcha, @RequestParam String token) {
        String loginToken = userService.login(loginName, password, captcha, token);
        return ApiResponse.successOfData(loginToken);
    }

    @GetMapping("/current")
    @ResponseBody
    public ApiResponse current() {
        User user = UserContext.getCurrent();
        if (user != null) {
            return ApiResponse.successOfData(user);
        }
        return ApiResponse.notLogin();
    }

    @GetMapping("/logout")
    @ResponseBody
    public ApiResponse logout() {
        UserContext.logout();
        return ApiResponse.successOfMessage("退出登录成功。");
    }

    @GetMapping("/ip")
    @ResponseBody
    public ApiResponse clientIp(HttpServletRequest request) {
        return ApiResponse.successOfData(IpUtils.getIpAddr(request));
    }

    @GetMapping("/test")
    @ResponseBody
    public ApiResponse test(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("current", LocalDateTime.now());

        System.out.println(request.getRequestURI());

//        throw error("出错了。。。");

        return ApiResponse.successOfData(data);
    }

    @GetMapping("/test2")
    @ResponseBody
    public ApiResponse test() {
        throw error("Error");
//        return ApiResponse.success();
    }
}
