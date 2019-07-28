package tech.ldxy.sin.system.context;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.ldxy.sin.system.config.SinConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 功能描述: 应用上下文
 *
 * @author hxulin
 */
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static RedisTemplate<String, Object> getRedisTemplate() {
        return AppContext.getBean("redisTemplate");
    }

    public static SinConfig getSinConfig() {
        return AppContext.getBean(SinConfig.class);
    }

    /**
     * 获取当前请求的 Request 对象
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前 Session 对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前请求的 Response 对象
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取 Spring 上下文对象
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名字获取 Spring 容器中注册的 Bean 实例
     * 如果找不到该 Bean 的实例, 则抛出 org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据类型获取 Spring 容器中注册的 Bean 实例
     * 如果找不到该 Bean 的实例, 则抛出 org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static <T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }

    /**
     * 根据名字查询 Spring 容器中是否存在某个 Bean 实例
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的 Bean 是 singleton 还是 prototype。
     * 如果与给定名字相应的 Bean 定义没有被找到，将抛出 org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name){
        return applicationContext.isSingleton(name);
    }

    /**
     * 根据 Spring 中注册的 Bean 的名称, 获取其类型
     * 如果找不到 Bean 的实例, 则抛出 org.springframework.beans.factory.NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name){
        return applicationContext.getType(name);
    }

    /**
     * 根据 Spring 中注册的 Bean 的名称, 获取其别名
     */
    public static String[] getAliases(String name){
        return applicationContext.getAliases(name);
    }

    /**
     * 获取 AOP 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker){
        return (T) AopContext.currentProxy();
    }
}
