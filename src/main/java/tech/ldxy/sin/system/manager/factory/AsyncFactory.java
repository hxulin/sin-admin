package tech.ldxy.sin.system.manager.factory;


import org.springframework.data.redis.core.RedisTemplate;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.model.vo.LoginInfo;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {

    private static SinConfig sinConfig = AppContext.getSinConfig();

    private static RedisTemplate<String, Object> redisTemplate = AppContext.getRedisTemplate();

    private AsyncFactory(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * 记录登录日志
     */
    public static TimerTask recordLoginLog(final LoginInfo loginInfo) {
        return new TimerTask() {
            @Override
            public void run() {
                // 根据 IP 查询登录地点

                System.out.println("---> 记录登录日志: " + loginInfo);
            }
        };
    }

    /**
     * 刷新缓存的 Token
     */
    public static TimerTask refreshToken(final String token) {
        return new TimerTask() {
            @Override
            public void run() {
                redisTemplate.expire(token, sinConfig.getTokenExpireTime(), TimeUnit.MINUTES);
            }
        };
    }

}
