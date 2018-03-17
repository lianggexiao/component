package com.qing.minisys.domain.page.dialect;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

/**
 * MySQL方言描述，构建MySQL分页SQL
 */
public class MySQLDialect extends AbstractDialect {
    
    private final String startStr = "select * from (";
    
    private final String endStr = ") alias LIMIT #{start}, #{limit}";

    @Override
    public String dialect() {
        return "mysql";
    }

    @Override
    public String getPageSql(String sql, int pageNo, int pageSize) throws SQLException {
        
        StringBuffer strB = new StringBuffer(startStr.length() + sql.length() + endStr.length());
        
        strB.append(startStr);
        strB.append(sql);
        strB.append(endStr);
        
        String newSql = strB.toString();
        newSql = StringUtils.replace(newSql, "#{start}", String.valueOf((pageNo - 1) * pageSize));
        newSql = StringUtils.replace(newSql, "#{limit}", String.valueOf(pageSize));
        
        return newSql;
    }

}
