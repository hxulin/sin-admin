package tech.ldxy.sin.system.manager.factory;


import org.springframework.data.redis.core.RedisTemplate;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.context.AppContext;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {

    private static RedisTemplate<String, Object> redisTemplate;

    private static SinConfig sinConfig;

    private AsyncFactory(){
        throw new IllegalStateException("Utility class");
    }

    static {
        sinConfig = AppContext.getBean(SinConfig.class);
        redisTemplate = AppContext.getBean("redisTemplate");
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
