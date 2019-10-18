package tech.ldxy.sin.system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import tech.ldxy.sin.framework.bean.ApiResponse;

import java.util.*;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
public class ResourceController {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public ResourceController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }


    @RequestMapping("/getAllUrl")
    public ApiResponse<List<String>> getAllUrl() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        List<String> urlList = new ArrayList<>();
        for (RequestMappingInfo requestMappingInfo : handlerMethods.keySet()) {
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            urlList.addAll(patterns);
        }
        return ApiResponse.successOfData(urlList);
    }
}
