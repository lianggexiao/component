package com.qing.minisys.domain.page.dialect;

import java.sql.SQLException;

/**
 * 方言接口，配置方言名称、判断方言是否匹配、获取分页SQL、获取记录数SQL
 */
public interface Dialect {

    public String dialect();
    
    public boolean skip(String dialect);
    
    public String getPageSql(String sql, int pageNo, int pageSize) throws SQLException;
    
    public String getCountSql(String sql) throws SQLException;
}
