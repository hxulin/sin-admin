package tech.ldxy.sin.system.manager.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.context.UserContext;
import tech.ldxy.sin.system.model.entity.OperateLog;
import tech.ldxy.sin.system.model.vo.LoginInfo;
import tech.ldxy.sin.system.service.IOperateLogService;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 异步工厂（产生任务用）
 *
 * @author hxulin
 */
@Slf4j
public class AsyncFactory {

    private static SinConfig sinConfig = AppContext.getSinConfig();

    private static RedisTemplate<String, Object> redisTemplate = AppContext.getRedisTemplate();

    private static IOperateLogService operateLogService = AppContext.getBean(IOperateLogService.class);

    private AsyncFactory() {

    }

    /**
     * 系统初始化
     */
    public static TimerTask systemInit() {
        return new TimerTask() {
            @Override
            public void run() {
                log.info("--------------- [2/3] 开始检测库表版本升级");
                AsyncFunction.databaseUpdateCheck();
                log.info("--------------- [2/3] 库表版本升级完成");
                log.info("--------------- [3/3] 开始注册系统新增的 URI");
                AsyncFunction.loadResource();
                log.info("--------------- [3/3] 系统新增的 URI 注册完成");
                log.info("--------------- 系统自检正常, 启动成功");
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

    /**
     * 记录日志
     */
    public static TimerTask recordLog(final OperateLog operateLog, final String token) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginInfo loginInfo = UserContext.getCurrentLoginInfo(token);
                    operateLog.setTrailId(loginInfo.getTrailId());
                    operateLog.setOperatorId(loginInfo.getId());
                    operateLog.setUsername(loginInfo.getLoginName());
                } catch (Exception ignored) {
                }
                operateLogService.save(operateLog);
            }
        };
    }

}
