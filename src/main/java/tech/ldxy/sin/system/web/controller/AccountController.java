package tech.ldxy.sin.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.core.exception.BusinessExceptionAware;
import tech.ldxy.sin.core.util.Captcha;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.core.util.IpUtils;
import tech.ldxy.sin.system.auth.OpenResource;
import tech.ldxy.sin.system.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@Controller
@OpenResource
public class AccountController implements BusinessExceptionAware {

    @GetMapping("/static/images/securityCode.png")
    public void code(HttpServletResponse response) throws IOException {
        // 设置响应的类型格式为图片格式
        response.setContentType("image/png");
        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        Captcha captcha = new Captcha(100, 38, 4, 10);
        // 将验证码信息保存到Session中
        UserContext.setCaptcha(captcha.getCode());
        captcha.write(response.getOutputStream());
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiResponse login(String username, String password) {
        UserContext.register(new User(username, password));
        return ApiResponse.successOfMessage("登录成功。");
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
        data.put("current", new Date());

        System.out.println(request.getRequestURI());

//        throw error("出错了。。。");

        return ApiResponse.successOfData(data);
    }

    @GetMapping("/test2")
    @ResponseBody
    public ApiResponse test() {
        throw error("23");
//        return ApiResponse.success();
    }
}
