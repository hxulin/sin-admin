package tech.ldxy.sin.system.web.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ldxy.sin.core.bean.ApiResponse;
import tech.ldxy.sin.core.util.encryption.AESUtils;
import tech.ldxy.sin.core.util.json.JSONUtils;
import tech.ldxy.sin.system.auth.OpenResource;

/**
 * 功能描述:
 *
 * @author hxulin
 */
@RestController
@Configuration
@ConfigurationProperties(prefix = "sin.system-admin")
@Data
public class SystemAdminController {

    /**
     * 匿名登录密钥的初始向量
     */
    private String iv;

    /**
     * 匿名登录的密钥
     */
    private String key;

    /**
     * 匿名登录口令
     */
    private String token;

    /**
     * 时间戳
     */
    private long timestamp;

    @GetMapping("/fastLogin")
    @OpenResource
    public ApiResponse fastLogin(String token) {
        if (StringUtils.isBlank(token)) {
            return ApiResponse.notValidParam();
        }
        try {
            String decrypt = AESUtils.decrypt(token, key, iv);
            JSONObject jsonObject = JSONUtils.fromJson(decrypt, JSONObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResponse.successOfMessage("登录成功。");
    }
}
