package tech.ldxy.sin.system.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tech.ldxy.sin.core.util.ListUtils;

import java.util.*;

/**
 * 功能描述: 应用启动监听器
 *
 * @author hxulin
 */
@Slf4j
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("--------------- 系统初始化, 开始自检 ---------------");
        ApplicationContext ctx = event.getApplicationContext();
        RequestMappingHandlerMapping handlerMapping = ctx.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            log.info("Load URI: {}", patterns);
            urlList.addAll(patterns);
        }
        List<String> elements = ListUtils.getDuplicateElements(urlList.stream());
        if (elements.size() > 0) {
            log.error("系统初始化错误, 存在重复的 URI: {}", elements);
            throw new RuntimeException("系统初始化错误, 存在重复的 URI: " + elements);
        }
        log.info("--------------- 初始化完成, 系统启动成功 ---------------");
    }
}
