package tech.ldxy.sin.core.util.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SimpleSerializerFilter extends SimplePropertyPreFilter {

    private Map<Class, Set<String>> includes = new HashMap<>();
    private Map<Class, Set<String>> excludes = new HashMap<>();

    SimpleSerializerFilter() {

    }

    @Override
    public boolean apply(JSONSerializer serializer, Object source, String name) {
        if (includes != null) {
            for (Entry<Class, Set<String>> include : includes.entrySet()) {
                Class objClass = include.getKey();
                Set<String> includeProp = include.getValue();
                if (objClass.isAssignableFrom(source.getClass())) {
                    return includeProp.contains(name);
                }
            }
        }
        if (excludes != null) {
            for (Entry<Class, Set<String>> exclude : excludes.entrySet()) {
                Class objClass = exclude.getKey();
                Set<String> includeProp = exclude.getValue();
                if (objClass.isAssignableFrom(source.getClass())) {
                    return !includeProp.contains(name);
                }
            }
        }
        return true;
    }

    public void include(Class clazz, String[] fields) {
        addToMap(includes, clazz, fields);
    }

    public void exclude(Class clazz, String[] fields) {
        addToMap(excludes, clazz, fields);
    }

    private void addToMap(Map<Class, Set<String>> map, Class clazz, String[] fields) {
        Set<String> fieldSet = map.get(clazz);
        if (fieldSet == null) {
            fieldSet = new HashSet<>();
        }
        fieldSet.addAll(Arrays.asList(fields));
        map.put(clazz, fieldSet);
    }
}
