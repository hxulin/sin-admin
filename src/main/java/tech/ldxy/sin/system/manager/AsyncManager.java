package tech.ldxy.sin.system.manager;

import tech.ldxy.sin.system.context.AppContext;

import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 异步任务管理器
 *
 * @author hxulin
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private static final int OPERATE_DELAY_TIME = 10;

    private AsyncManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 异步操作任务调度线程池
     */
    private static ScheduledExecutorService executorService;

    static {
        executorService = AppContext.getBean(ScheduledExecutorService.class);
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public static void execute(TimerTask task) {
        executorService.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 执行异步任务, 要求返回结果
     */
    public static <V> Future<V> execute(Callable<V> callable) {
        return executorService.submit(callable);
    }

}
