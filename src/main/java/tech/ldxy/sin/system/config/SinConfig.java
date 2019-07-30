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

    private int tokenExpireTime = 30;

    private int captchaExpireTime = 10;

    private String loginPasswordSalt = "";

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

    public int getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(int tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public int getCaptchaExpireTime() {
        return captchaExpireTime;
    }

    public void setCaptchaExpireTime(int captchaExpireTime) {
        this.captchaExpireTime = captchaExpireTime;
    }

    public String getLoginPasswordSalt() {
        return loginPasswordSalt;
    }

    public void setLoginPasswordSalt(String loginPasswordSalt) {
        this.loginPasswordSalt = loginPasswordSalt;
    }
}
