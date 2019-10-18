package tech.ldxy.sin.framework.model;

import tech.ldxy.sin.framework.util.BeanConverter;

import java.io.Serializable;

/**
 * 功能描述: 普通实体的父类
 *
 * @author hxulin
 */
public class Convert implements Serializable {

    private static final long serialVersionUID = 1L;

    public <T> T convert(Class<T> clazz) {
        return BeanConverter.convert(clazz, this);
    }
}
