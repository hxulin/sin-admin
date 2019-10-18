package tech.ldxy.sin.framework.model.query;

import java.util.Date;

/**
 * 功能描述: 带时间范围的高级查询对象
 *
 * @author hxulin
 */
public class DateQueryObject extends QueryObject {

    /**
     * 开始时间
     */
    protected Date startTime;

    /**
     * 结束时间
     */
    protected Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
