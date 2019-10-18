package tech.ldxy.sin.framework.util;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cglib.beans.BeanMap;
import tech.ldxy.sin.framework.modelmapper.jdk8.Jdk8Module;
import tech.ldxy.sin.framework.modelmapper.jsr310.Jsr310Module;
import tech.ldxy.sin.framework.modelmapper.jsr310.Jsr310ModuleConfig;

import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 转换工具类
 *
 * @author hxulin
 */
public class BeanConverter {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        Jsr310ModuleConfig config = Jsr310ModuleConfig.builder()
                .dateTimePattern("yyyy-MM-dd HH:mm:ss")  // default is yyyy-MM-dd HH:mm:ss
                .datePattern("yyyy-MM-dd")  // default is yyyy-MM-dd
                .zoneId(ZoneOffset.UTC)  // default is ZoneId.systemDefault()
                .build();
        modelMapper.registerModule(new Jsr310Module(config)).registerModule(new Jdk8Module());
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * 获取 ModelMapper
     */
    public static ModelMapper getModelMapper() {
        return modelMapper;
    }

    /**
     * Bean 转换为 Map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Collections.emptyMap();
        if (null != bean) {
            BeanMap beanMap = BeanMap.create(bean);
            map = new HashMap<>(beanMap.keySet().size());
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * List<T> 转换为 List<Map<String, Object>>
     */
    public static <T> List<Map<String, Object>> beansToMap(List<T> objList) {
        List<Map<String, Object>> list = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(objList)) {
            list = new ArrayList<>(objList.size());
            Map<String, Object> map;
            T bean;
            for (T anObjList : objList) {
                bean = anObjList;
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * List<Map<String, Object>> 转换为 List<T>
     */
    public static <T> List<T> mapToBean(List<Map<String, Object>> mapList, Class<T> beanClass) {
        List<T> list = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(mapList)) {
            list = new ArrayList<>(mapList.size());
            Map<String, Object> map;
            T bean;
            for (Map<String, Object> map1 : mapList) {
                map = map1;
                bean = mapToBean(map, beanClass);
                list.add(bean);
            }
        }
        return list;
    }

    /**
     * Map 转换为 Bean
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        T entity = ClassUtils.newInstance(beanClass);
        BeanMap beanMap = BeanMap.create(entity);
        beanMap.putAll(map);
        return entity;
    }

    /**
     * 列表转换
     */
    public static <T> List<T> convert(Class<T> clazz, List<?> list) {
        return CollectionUtils.isEmpty(list) ? Collections.emptyList() : list.stream().map(e -> convert(clazz, e)).collect(Collectors.toList());
    }

    /**
     * 单个对象转换
     *
     * @param targetClass 目标对象
     * @param source      源对象
     * @return 转换后的目标对象
     */
    public static <T> T convert(Class<T> targetClass, Object source) {
        return getModelMapper().map(source, targetClass);
    }

}
