package tech.ldxy.sin.system.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.ldxy.sin.system.web.filter.TokenFilter;

/**
 * 功能描述: 过滤器相关配置
 *
 * @author hxulin
 */
@Configuration
public class WebFilterConfig {

    /**
     * 注册 Token Filter
     */
    @Bean
    public FilterRegistrationBean<TokenFilter> xssFilterFilterRegistrationBean() {
        TokenFilter tokenFilter = new TokenFilter();
        FilterRegistrationBean<TokenFilter> registration = new FilterRegistrationBean<>(tokenFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
