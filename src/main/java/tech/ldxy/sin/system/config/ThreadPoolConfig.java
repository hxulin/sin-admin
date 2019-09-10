package tech.ldxy.sin.system.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import tech.ldxy.sin.system.util.Threads;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 功能描述: 线程池相关配置
 *
 * @author hxulin
 */
@Configuration
@ConfigurationProperties(prefix = "sin.config.thread-pool")
@Getter
@Setter
public class ThreadPoolConfig {

    /**
     * 空闲线程存活时间，单位：s 默认2000
     */
    private int keepAliveSeconds = 2000;

    /**
     * 线程池维护线程最大数量 默认100
     */
    private int maxPoolSize = 100;

    /**
     * 线程池维护线程最小数量 默认10
     */
    private int corePoolSize = 10;

    @Bean(name = "publicThreadPool")
    protected ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        return executor;
    }

    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduleTaskExecutor() {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()){
            @Override
            protected void afterExecute(Runnable r, Throwable t){
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }

}
