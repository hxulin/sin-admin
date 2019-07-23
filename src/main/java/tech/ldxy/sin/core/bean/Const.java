package tech.ldxy.sin.core.bean;

/**
 * 功能描述: 系统数据字典常量
 *
 * @author hxulin
 */
public interface Const {
	
    /**
     * 日期格式化类型
     */
	interface DateFormat {

        String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

        String DATE_PATTERN = "yyyy-MM-dd";

        String TIME_PATTERN = "HH:mm:ss";

        String MINUTE_PATTEN = "yyyy-MM-dd HH:mm";
	}

    /**
     * 字符编码
     */
	interface Charset {
	    String UTF_8 = "UTF-8";
    }

}
