package tech.ldxy.sin.system.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import tech.ldxy.sin.core.bean.Status;
import tech.ldxy.sin.core.util.IpUtils;
import tech.ldxy.sin.core.util.MD5;
import tech.ldxy.sin.core.util.encryption.AESUtils;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.manager.AsyncManager;
import tech.ldxy.sin.system.manager.factory.AsyncFactory;
import tech.ldxy.sin.system.model.entity.Menu;
import tech.ldxy.sin.system.model.entity.User;
import tech.ldxy.sin.system.model.vo.LoginInfo;
import tech.ldxy.sin.system.util.SinAssert;
import tech.ldxy.sin.system.web.filter.ContextManager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述: 用户上下文
 *
 * @author hxulin
 */
public final class UserContext {

    private static SinConfig sinConfig = AppContext.getSinConfig();

    private static RedisTemplate<String, Object> redisTemplate = AppContext.getRedisTemplate();

    private static final String CAPTCHA_PREFIX = "captcha:";

    private static final String TOKEN_PREFIX = "token:";

    private static final String USER_PREFIX = "user:";

    private static final String RESOURCE_SUFFIX = ":resource";

    private static final String MENU_SUFFIX = ":menu";

    private UserContext() {

    }

    /**
     * 获取缓存中的验证码
     */
    public static String getCaptcha(String key) {
        return (String) redisTemplate.opsForValue().get(CAPTCHA_PREFIX + key);
    }

    /**
     * 保存登录页面的验证码到缓存中
     */
    public static void setCaptcha(String key, String captcha) {
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX + key, captcha, sinConfig.getCaptchaExpireTime(), TimeUnit.MINUTES);
    }

    /**
     * 从缓存中删除验证码信息
     */
    public static void removeCaptcha(String key) {
        redisTemplate.delete(CAPTCHA_PREFIX + key);
    }

    /**
     * 用户登录，将用户的登录信息保存到缓存中
     */
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
        // 异步记录登录日志
        AsyncManager.execute(AsyncFactory.recordLoginLog(loginInfo));
        redisTemplate.opsForValue().set(TOKEN_PREFIX + token, loginInfo, sinConfig.getTokenExpireTime(), TimeUnit.MINUTES);
        return token;
    }

    /**
     * 刷新 Token 的过期时间
     */
    public static void refreshToken() {
        String token = ContextManager.getAttribute(Constant.TOKEN_KEY);
        if (StringUtils.isNotEmpty(token)) {
            // TODO
            // TODO
            // TODO
            System.err.println("-------> refresh token");
//            AsyncManager.execute(AsyncFactory.refreshToken(TOKEN_PREFIX + token));
        }
    }

    /**
     * 获取当前用户的登录信息
     */
    public static LoginInfo getCurrentLoginInfo() {
        Object token = ContextManager.getAttribute(Constant.TOKEN_KEY);
        SinAssert.INSTANCE.assertNotNull(token, Status.NOT_LOGIN);
        Object loginInfo = redisTemplate.opsForValue().get(TOKEN_PREFIX + token);
        SinAssert.INSTANCE.assertNotNull(loginInfo, Status.NOT_LOGIN);
        return (LoginInfo) loginInfo;
    }

    /**
     * 获取当前登录用户的ID
     */
    public static Long getCurrentUid() {
        return getCurrentLoginInfo().getId();
    }

    /**
     * 用户注销登录
     */
    public static void logout() {
        String token = ContextManager.getAttribute(Constant.TOKEN_KEY);
        if (StringUtils.isNotEmpty(token)) {
            redisTemplate.delete(TOKEN_PREFIX + token);
        }
    }

    /**
     * 将用户的权限列表信息保存到缓存中
     */
    public static void cachePermissionList(Long uid, List<String> resourceMapping) {
        String resourceKey = USER_PREFIX + uid + RESOURCE_SUFFIX;
        redisTemplate.opsForSet().add(resourceKey, resourceMapping.toArray(new String[0]));
    }

    /**
     * 检查当前用户是否具有访问该资源的权限
     */
    public static boolean hasPermission(String requestURI) {
        String resourceKey = USER_PREFIX + getCurrentUid() + RESOURCE_SUFFIX;
        return redisTemplate.opsForSet().isMember(resourceKey, requestURI);
    }

    /**
     * 将用户的菜单列表信息保存到缓存中
     */
    public static void cacheMenuList(Long uid, List<Menu> menuList) {
        String menuKey = USER_PREFIX + uid + MENU_SUFFIX;
        redisTemplate.opsForValue().set(menuKey, menuList);
    }

    /**
     * 获取当前登录用户的菜单列表信息
     */
    @SuppressWarnings("unchecked")
    public static List<Menu> getCurrentMenuList() {
        String menuKey = USER_PREFIX + getCurrentUid() + MENU_SUFFIX;
        return (List<Menu>) redisTemplate.opsForValue().get(menuKey);
    }

    /**
     * 加密登录密码
     */
    public static String encryptPwd(String password) {
        String saltPwd = password + sinConfig.getLoginPasswordSalt();
        return MD5.md5(saltPwd);
    }
}
