package tech.ldxy.sin.system.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: MVC 相关配置
 *
 * @author hxulin
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /**
     * 将 FastJson 设为默认的 JSON 解析器
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 1.构建了一个 HttpMessageConverter FastJSON 消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        // 2.定义一个配置，设置编码方式和格式化的形式
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        // 3.设置 JSON 解析的参数配置
        fastJsonConfig.setSerializerFeatures(
                // 禁用循环引用检测
                SerializerFeature.DisableCircularReferenceDetect,
                // 时间输出格式：yyyy-MM-dd HH:mm:ss
                SerializerFeature.WriteDateUseDateFormat,
                // 显示空字段
                SerializerFeature.WriteMapNullValue,
                // 字符串类型字段为null时, 输出"", 而非null
                SerializerFeature.WriteNullStringAsEmpty,
                // List类型字段为null时, 输出[], 而非null
                SerializerFeature.WriteNullListAsEmpty,
                // 数值字段如果为null, 输出为0, 而非null
                // SerializerFeature.WriteNullNumberAsZero,
                // Boolean字段如果为null, 输出为false, 而非null
                SerializerFeature.WriteNullBooleanAsFalse
        );

        // 4.处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        // 5.将 FastJSONConfig 加到消息转换器中
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
}
