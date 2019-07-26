package tech.ldxy.sin.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述: 系统相关配置信息
 *
 * @author hxulin
 */
@Configuration
@ConfigurationProperties(prefix = "sin.config")
public class SinConfig {

    private String tokenAesKey;

    private String tokenAesIv;

    public String getTokenAesKey() {
        return tokenAesKey;
    }

    public void setTokenAesKey(String tokenAesKey) {
        this.tokenAesKey = tokenAesKey;
    }

    public String getTokenAesIv() {
        return tokenAesIv;
    }

    public void setTokenAesIv(String tokenAesIv) {
        this.tokenAesIv = tokenAesIv;
    }
}
