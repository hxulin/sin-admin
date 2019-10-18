package tech.ldxy.sin.system.util;

import tech.ldxy.sin.framework.bean.Status;
import tech.ldxy.sin.framework.exception.BusinessExceptionAware;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public enum SinAssert implements BusinessExceptionAware {

    INSTANCE;

    public void assertNotNull(Object object, Status status) {
        if (object == null) {
            throw error(status);
        }
    }
}
