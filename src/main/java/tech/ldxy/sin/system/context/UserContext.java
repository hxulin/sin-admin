package tech.ldxy.sin.system.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tech.ldxy.sin.core.util.IpUtils;
import tech.ldxy.sin.core.util.encryption.AESUtils;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.model.vo.LoginInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 用户上下文
 *
 * @author hxulin
 */
@Component
public final class UserContext {

    private static RedisTemplate<String, Object> redisTemplate;

    private static SinConfig sinConfig;

    private static final String CAPTCHA_PREFIX = "captcha:";

    private static final String TOKEN_PREFIX = "token:";

    private static final String LOGIN_INFO_IN_SESSION = "login_info_in_session";


    private UserContext() {

    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        UserContext.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setSinConfig(SinConfig sinConfig) {
        UserContext.sinConfig = sinConfig;
    }

    // 获取缓存中的验证码
    public static String getCaptcha(String key) {
        return (String) redisTemplate.opsForValue().get(CAPTCHA_PREFIX + key);
    }

    // 保存登录页面的验证码到缓存中
    public static void setCaptcha(String key, String captcha) {
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX + key, captcha, 10, TimeUnit.MINUTES);
    }

    public static void removeCaptcha(String key) {
        redisTemplate.delete(CAPTCHA_PREFIX + key);
    }

    // 用户登录，将用户的登录信息保存到缓存中
    public static String login(User user) {
        Date current = new Date();
        String loginIp = IpUtils.getIpAddr(AppContext.getRequest());
        String token = String.join(":", new String[]{
                String.valueOf(current.getTime()), user.getLoginName(), loginIp
        });
        try {
            token = AESUtils.encrypt(token, sinConfig.getTokenAesKey(), sinConfig.getTokenAesIv());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LoginInfo loginInfo = user.convert(LoginInfo.class);
        loginInfo.setLoginTime(current);
        loginInfo.setLoginIp(loginIp);
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, loginInfo, 30, TimeUnit.MINUTES);
        return token;
    }

    // 获取当前登录信息
    public static LoginInfo getCurrentLoginInfo() {
        return (LoginInfo) AppContext.getSession().getAttribute(LOGIN_INFO_IN_SESSION);
    }

    // 获取当前登录用户信息
    public static User getCurrent() {
        return getCurrentLoginInfo().convert(User.class);
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
