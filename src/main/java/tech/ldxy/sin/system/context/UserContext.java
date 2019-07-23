package tech.ldxy.sin.system.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import tech.ldxy.sin.core.util.IpUtils;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.model.vo.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 功能描述: 用户上下文
 *
 * @author hxulin
 */
public final class UserContext {

    private static final String CAPTCHA_IN_SESSION = "captcha_in_session";

    private static final String LOGIN_INFO_IN_SESSION = "login_info_in_session";

    private UserContext() {

    }

    // 保存登录页面的验证码到Session中
    public static void setCaptcha(String captcha) {
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        if (captcha == null) {
            attributes.removeAttribute(CAPTCHA_IN_SESSION, RequestAttributes.SCOPE_SESSION);
        } else {
            attributes.setAttribute(CAPTCHA_IN_SESSION, captcha, RequestAttributes.SCOPE_SESSION);
        }
    }

    // 检验Session中验证码是否正确
    public static boolean checkCaptcha(String captcha) {
        if (captcha == null) {
            return false;
        }
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        return captcha.equals(attributes.getAttribute(CAPTCHA_IN_SESSION, RequestAttributes.SCOPE_SESSION));
    }

    // 获取当前登录信息
    public static LoginInfo getCurrentLoginInfo() {
        return (LoginInfo) AppContext.getSession().getAttribute(LOGIN_INFO_IN_SESSION);
    }

    // 获取当前登录用户信息
    public static User getCurrent() {
        return getCurrentLoginInfo();
    }

    // 用户登录
    public static void login(User user) {
        LoginInfo loginInfo = user.convert(LoginInfo.class);
        loginInfo.setLoginIp(IpUtils.getIpAddr(AppContext.getRequest()));
        loginInfo.setLoginTime(new Date());
        AppContext.getSession().setAttribute(LOGIN_INFO_IN_SESSION, loginInfo);
    }

    // 用户注销登录
    public static void logout() {
        AppContext.getSession().invalidate();
    }

    /**
     * 检查当前用户是否具有访问本资源的权限
     */
    public static boolean hasPermission(String requestURI) {
        HttpServletRequest request = AppContext.getRequest();
        String token = request.getHeader(Constant.TOKEN_KEY);
        System.out.println(token);
        return requestURI != null;
    }
}
