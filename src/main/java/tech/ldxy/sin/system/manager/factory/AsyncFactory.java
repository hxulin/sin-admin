package tech.ldxy.sin.system.manager.factory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.system.auth.AuthType;
import tech.ldxy.sin.system.auth.Resources;
import tech.ldxy.sin.system.config.SinConfig;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.model.vo.LoginInfo;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 异步工厂（产生任务用）
 *
 * @author liuhulu
 */
public class AsyncFactory {

    private static SinConfig sinConfig = AppContext.getSinConfig();

    private static RedisTemplate<String, Object> redisTemplate = AppContext.getRedisTemplate();

    private AsyncFactory() {
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


    /**
     * 加载资源
     */
    public static Callable<Object> loadResource() {
        return () -> {
            String resourceBasePackage = "tech.ldxy.sin.system.web.controller";
            ClassPathScanningCandidateComponentProvider provider =
                    new ClassPathScanningCandidateComponentProvider(false);  // 不使用默认的TypeFilter
            provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
            provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
            Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(resourceBasePackage);
            Map<String, AuthType> authInfo = new HashMap<>();
            beanDefinitionSet.forEach(item -> {
                try {
                    Class<?> clazz = Class.forName(item.getBeanClassName());
                    Resources resources = clazz.getAnnotation(Resources.class);
                    AuthType classAuthType = resources != null ? resources.value() : AuthType.OPEN;
                    RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                    Set<String> resourcePrefix = handleMappingUri(mapping);
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (requestMapping != null || getMapping != null || postMapping != null) {
                            Resources methodResources = method.getAnnotation(Resources.class);
                            AuthType authType = methodResources != null ? methodResources.value() : classAuthType;
                            Set<String> resourceSuffix = new HashSet<>();
                            resourceSuffix.addAll(handleMappingUri(requestMapping));
                            resourceSuffix.addAll(handleMappingUri(getMapping));
                            resourceSuffix.addAll(handleMappingUri(postMapping));
                            resourceSuffix.forEach(pathSuffix -> {
                                if (resourcePrefix.size() > 0) {
                                    resourcePrefix.forEach(pathPrefix ->
                                            authInfo.put(pathPrefix + pathSuffix, authType));
                                } else {
                                    authInfo.put(pathSuffix, authType);
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println(authInfo);
//            return ApiResponse.success();
            return null;
        };
    }

    /**
     * 处理Controller的Mapping注解
     */
    private static Set<String> handleMappingUri(Object mapping) {
        String[] value = null;
        String[] path = null;
        if (mapping instanceof RequestMapping) {
            RequestMapping requestMapping = (RequestMapping) mapping;
            value = requestMapping.value();
            path = requestMapping.path();
        } else if (mapping instanceof GetMapping) {
            GetMapping getMapping = (GetMapping) mapping;
            value = getMapping.value();
            path = getMapping.path();

        } else if (mapping instanceof PostMapping) {
            PostMapping postMapping = (PostMapping) mapping;
            value = postMapping.value();
            path = postMapping.path();
        }
        Set<String> resourcePath = new HashSet<>();
        if (value != null) {
            resourcePath.addAll(Arrays.asList(value));
        }
        if (path != null) {
            resourcePath.addAll(Arrays.asList(path));
        }
        return resourcePath;
    }

}
