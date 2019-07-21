package tech.ldxy.sin.core.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 功能描述: 集合 List 工具类
 *
 * @author hxulin
 */
public class ListUtils {

    private ListUtils() {

    }

    /**
     * 获取 List 中重复的元素
     */
    public static <T> List<T> getDuplicateElements(Stream<T> stream) {
        return stream
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
