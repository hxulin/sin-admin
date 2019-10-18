package tech.ldxy.sin.system.manager.factory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.framework.bean.Const;
import tech.ldxy.sin.framework.util.json.JSONUtils;
import tech.ldxy.sin.system.auth.AuthType;
import tech.ldxy.sin.system.auth.Resources;
import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.context.AppContext;
import tech.ldxy.sin.system.model.entity.Conf;
import tech.ldxy.sin.system.model.entity.Resource;
import tech.ldxy.sin.system.service.IConfService;
import tech.ldxy.sin.system.service.IResourceService;
import tech.ldxy.sin.system.service.ISchemaUpdateService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 功能描述: 为异步工厂提供模块功能的实现
 *
 * @author HuangXuLin
 */
final class AsyncFunction {

    private static final String SCHEMA_FILE_NAME = "db/schema.md";

    private static IConfService confService = AppContext.getBean(IConfService.class);

    private static ISchemaUpdateService schemaUpdateService = AppContext.getBean(ISchemaUpdateService.class);

    private AsyncFunction() {

    }

    // ==================== 检测库表版本更新 ↓↓↓

    /**
     * 数据库更新检测
     */
    static void databaseUpdateCheck() {
        TreeMap<Integer, List<String>> schemaInfo;
        try {
            schemaInfo = parseSchemaFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int currentVersion;
        if (!schemaUpdateService.sysConfTableIsExist()) {
            currentVersion = schemaInfo.firstKey();
            List<String> sqlList = schemaInfo.firstEntry().getValue();
            schemaUpdateService.updateDbSchema(sqlList);
            Conf conf = new Conf(Constant.Conf.DB_SCHEMA_VERSION, String.valueOf(currentVersion),
                    Const.Status.NORMAL, "记录库表更新的 SQL 版本号");
            confService.save(conf);
        }
        Conf conf = confService.getOne(new QueryWrapper<Conf>().eq("NAME",
                Constant.Conf.DB_SCHEMA_VERSION));
        currentVersion = Integer.valueOf(conf.getValue());
        if (currentVersion < schemaInfo.lastKey()) {
            for (int key = currentVersion + 1; key <= schemaInfo.lastKey(); key++) {
                if (schemaInfo.containsKey(key)) {
                    schemaUpdateService.updateDbSchema(schemaInfo.get(key));
                    conf.setValue(String.valueOf(key));
                    confService.saveOrUpdate(conf);
                }
            }
        }
    }

    /**
     * 解析文件节点
     */
    private static String parseSchemaNode(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("```")) {
                break;
            }
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 解析库表更新的 Schema 文件
     */
    @SuppressWarnings("unchecked")
    private static TreeMap<Integer, List<String>> parseSchemaFile() throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource(SCHEMA_FILE_NAME);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream(), Const.Charset.UTF_8);
        List<String> explain = new ArrayList<>();
        List<String> sql = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("```json")) {
                    explain.add(parseSchemaNode(br));
                } else if (line.startsWith("```sql")) {
                    sql.add(parseSchemaNode(br));
                }
            }
        }
        if (explain.size() != sql.size()) {
            throw new RuntimeException("库表变更文件 schema.md 格式错误。");
        }
        TreeMap<Integer, List<String>> result = new TreeMap<>();
        for (int i = 0; i < explain.size(); i++) {
            Map<String, Object> exp = JSONUtils.fromJson(explain.get(i), HashMap.class);
            Integer version = (Integer) exp.get("version");
            if (result.containsKey(version)) {
                throw new RuntimeException("库表变更文件 schema.md 存在相同的版本号: " + version);
            }
            if (version > 0) {
                result.put(version, Arrays.asList(sql.get(i).split(";")));
            }
        }
        return result;
    }
    // ==================== 检测库表版本更新 ↑↑↑

    // ==================== 加载系统资源 ↓↓↓

    /**
     * 加载资源
     */
    static void loadResource() {
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
    // ==================== 加载系统资源 ↑↑↑

}
