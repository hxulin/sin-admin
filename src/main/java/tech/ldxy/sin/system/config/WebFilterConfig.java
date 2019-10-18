package tech.ldxy.sin.system.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ldxy.sin.framework.web.filter.XssFilter;
import tech.ldxy.sin.system.web.filter.TokenFilter;

/**
 * 功能描述: 过滤器相关配置
 *
 * @author hxulin
 */
@Configuration
public class WebFilterConfig {

    /**
     * 注册 Xss Filter
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean() {
        XssFilter xssFilter = new XssFilter();
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(xssFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 注册 Token Filter
     */
    @Bean
    public FilterRegistrationBean<TokenFilter> tokenFilterFilterRegistrationBean() {
        TokenFilter tokenFilter = new TokenFilter();
        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>(tokenFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        return registration;
    }

}
