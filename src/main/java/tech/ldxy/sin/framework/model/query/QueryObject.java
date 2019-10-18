package tech.ldxy.sin.framework.model.query;

/**
 * 功能描述: 高级查询对象
 *
 * @author hxulin
 */
public class QueryObject {

    public static final String ORDER_TYPE_ASC = "ASC";

    public static final String ORDER_TYPE_DESC = "DESC";

    /**
     * 当前页数
     */
    protected int currentPage = 1;

    /**
     * 分页大小
     */
    protected int pageSize = 10;

    /**
     * 关键字查询项
     */
    protected String keyword;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
