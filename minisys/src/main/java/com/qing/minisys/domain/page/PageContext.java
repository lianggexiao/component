package com.qing.minisys.domain.page;

/**
 * 分页功能上下文
 */
public class PageContext {

    private final static ThreadLocal<PageContext> THREAD_LOCAL = new ThreadLocal<PageContext>() {

        @Override
        protected PageContext initialValue() {
            return new PageContext();
        }
    };
    
    private String sqlId;
    private boolean isQueryTotal = false;
    private boolean isPageable = false;
    private Page<?> page;
    
    public final static PageContext getPageContext() {
        return THREAD_LOCAL.get();
    }
    
    public final void remove() {
        THREAD_LOCAL.remove();
    }
    
    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public boolean isPageable() {
        return isPageable;
    }

    public void setPageable(boolean isPageable) {
        this.isPageable = isPageable;
    }

    public Page<?> getPage() {
        return page;
    }

    public void setPage(Page<?> page) {
        this.page = page;
    }

    public boolean isQueryTotal() {
        return isQueryTotal;
    }

    public void setQueryTotal(boolean isQueryTotal) {
        this.isQueryTotal = isQueryTotal;
    }
    
}
