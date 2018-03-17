package com.qing.minisys.domain.page.dialect;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

/**
 * 方言抽象类，封装判断方言是否匹配、获取查询总数据量的方法
 */
public abstract class AbstractDialect implements Dialect {

    private final String startStr = "select count(1) from (";
    private final String endStr = ") alias";
    
    @Override
    public boolean skip(String dialect) {
        if (StringUtils.isEmpty(dialect)) {
            return false;
        }
        return dialect.toUpperCase().equals(this.dialect().toUpperCase());
    }

    @Override
    public String getCountSql(String sql) throws SQLException {

        if(StringUtils.isEmpty(sql)) {
            throw new SQLException("page plugin get records count sql error");
        }
        
        StringBuffer strB = new StringBuffer(startStr.length() + sql.length() + endStr.length());
        
        strB.append(startStr); 
        strB.append(sql);
        strB.append(endStr);
        return strB.toString();
    }
}
