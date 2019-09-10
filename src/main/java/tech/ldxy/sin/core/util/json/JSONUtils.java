package tech.ldxy.sin.core.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 功能描述: JSON序列化和反序列化的工具类
 *
 * @author hxulin
 */
public final class JSONUtils {

    private static final SerializerFeature[] SERIALIZE_FEATURES = {
            // 禁用循环引用检测
            SerializerFeature.DisableCircularReferenceDetect,
            // 时间类型格式化为 yyyy-MM-dd HH:mm:ss
            SerializerFeature.WriteDateUseDateFormat
    };

    private JSONUtils() {

    }

    private static void addFilterRule(Class<?> entryClazz, String[] includes, String[] excludes, SimpleSerializerFilter filter) {
        if (includes.length > 0 && excludes.length > 0) {
            throw new IncludeAndExcludeConflictException("Can not use both include field and exclude field in a serialization strategy.");
        } else if (includes.length > 0) {
            filter.include(entryClazz, includes);
        } else if (excludes.length > 0) {
            filter.exclude(entryClazz, excludes);
        }
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param object 需要序列化的对象
     * @return 序列化后的JSON字符串
     */
    public static String toJson(Object object) {
        return JSON.toJSONString(object, SERIALIZE_FEATURES);
    }

    /**
     * 根据指定的序列化策略, 将Java对象序列化为JSON字符串
     *
     * @param object     需要序列化的对象
     * @param strategies 序列化策略, 在序列化时指定包含或排除哪些属性
     * @return 序列化后的JSON字符串
     */
    public static String toJson(Object object, SerializationStrategy... strategies) {
        SimpleSerializerFilter filter = new SimpleSerializerFilter();
        if (strategies != null) {
            for (SerializationStrategy strategy : strategies) {
                addFilterRule(strategy.getType(), strategy.getInclude(), strategy.getExclude(), filter);
            }
            return JSON.toJSONString(object, filter, SERIALIZE_FEATURES);
        }
        return toJson(object);
    }

    /**
     * 将字符串反序列化为Java对象
     *
     * @param text  需要反序列化的字符串
     * @param clazz 需要反序列化生成的对象类型
     * @param <T>   对象泛型
     * @return 反序列化生成的对象
     */
    public static <T> T fromJson(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

}
