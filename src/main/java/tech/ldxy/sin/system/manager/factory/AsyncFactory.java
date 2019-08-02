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
import tech.ldxy.sin.system.model.entity.Resource;
import tech.ldxy.sin.system.model.vo.LoginInfo;
import tech.ldxy.sin.system.service.IResourceService;

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
    public static Callable<Boolean> loadResource() {
        return () -> {
            String resourceBasePackage = "tech.ldxy.sin.system.web.controller";
            ClassPathScanningCandidateComponentProvider provider =
                    new ClassPathScanningCandidateComponentProvider(false);  // 不使用默认的TypeFilter
            provider.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
            provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
            Set<BeanDefinition> beanDefinitionSet = provider.findCandidateComponents(resourceBasePackage);
            Set<Resource> authInfo = new HashSet<>();
            beanDefinitionSet.forEach(item -> {
                try {
                    Class<?> clazz = Class.forName(item.getBeanClassName());
                    Resources resources = clazz.getAnnotation(Resources.class);
                    AuthType classAuthType = resources != null ? resources.auth() : AuthType.OPEN;
                    RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                    Set<String> resourcePrefix = handleMappingUri(mapping);
                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        if (requestMapping != null || getMapping != null || postMapping != null) {
                            Resources methodResources = method.getAnnotation(Resources.class);
                            AuthType authType = methodResources != null ? methodResources.auth() : classAuthType;
                            String resourceName = methodResources != null ? methodResources.name() : null;
                            Set<String> resourceSuffix = new HashSet<>();
                            resourceSuffix.addAll(handleMappingUri(requestMapping));
                            resourceSuffix.addAll(handleMappingUri(getMapping));
                            resourceSuffix.addAll(handleMappingUri(postMapping));
                            resourceSuffix.forEach(pathSuffix -> {
                                if (resourcePrefix.size() > 0) {
                                    resourcePrefix.forEach(pathPrefix ->
                                            authInfo.add(buildResource(resourceName, pathPrefix + pathSuffix, authType)));
                                } else {
                                    authInfo.add(buildResource(resourceName, pathSuffix, authType));
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            IResourceService resourceService = AppContext.getBean(IResourceService.class);
            List<Resource> resourceList = resourceService.list();
            authInfo.removeAll(resourceList);
            resourceService.saveBatch(authInfo);
            return true;
        };
    }

    /**
     * 构建 Resource 资源对象
     */
    private static Resource buildResource(String resourceName, String path, AuthType authType) {
        Resource resource = new Resource();
        if (resourceName != null) {
            resource.setName(resourceName);
        }
        resource.setMapping(path);
        resource.setAuthType(authType.getValue());
        return resource;
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
