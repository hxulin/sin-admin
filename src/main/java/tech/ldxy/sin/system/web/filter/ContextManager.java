package tech.ldxy.sin.system.web.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 上下文信息管理器
 *
 * @author hxulin
 */
public final class ContextManager {

    private static ThreadLocal<Map<String, Object>> tl = new ThreadLocal<>();

    private ContextManager() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(String key) {
        return (T) getContext().get(key);
    }

    public static void addAttribute(String key, Object value) {
        getContext().put(key, value);
    }

    public static void removeAttribute(String key) {
        getContext().remove(key);
    }

    static Map<String, Object> getContext() {
        if (tl.get() == null) {
            tl.set(new HashMap<>());
        }
        return tl.get();
    }

    static void removeContext() {
        if (tl.get() != null) {
            tl.remove();
        }
    }
}
