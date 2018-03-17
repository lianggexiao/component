package com.qing.minisys.domain.page.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.qing.minisys.domain.page.Page;
import com.qing.minisys.domain.page.PageContext;
import com.qing.minisys.domain.page.PageException;
import com.qing.minisys.domain.page.dialect.Dialect;
import com.qing.minisys.domain.page.dialect.DialectFactory;
import com.qing.minisys.domain.page.dialect.MySQLDialect;
import com.qing.minisys.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * Mybatis分页拦截器，分页核心代码
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

    private Properties properties;
    /*
     * 数据库类型，不同的数据库有不同的分页方法
     */
    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        PageContext pageContext = PageContext.getPageContext();
        if (null != pageContext && pageContext.isPageable()) {
            //判断该拦截器需要执行分页
            String pageSqlId = pageContext.getSqlId();
            if(StringUtils.isEmpty(pageSqlId)) {
                throw new PageException("parameter 'sqlId' in @Pageable is required");
            }

            Page<?> page = null;
            
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            BoundSql boundSql = handler.getBoundSql();
            MetaObject metaObj = MetaObject.forObject(handler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
            
            Object params = boundSql.getParameterObject();
            
            MappedStatement mappedStatement = null;
            if(params instanceof Page<?>) {
                //如果入参就是Page对象
                page = (Page<?>) params;
            } else {
                PreparedStatementHandler pHandler = (PreparedStatementHandler) ReflectionUtils.getFieldValue(handler, "delegate");
                mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(pHandler, "mappedStatement");
                String sqlId = mappedStatement.getId();
                if(pageSqlId.equals(sqlId)) {
                   page = pageContext.getPage();
                }
            }
            
            if(null != page) {
                String sql = boundSql.getSql();
                if(pageContext.isQueryTotal()) {
                    //需要查询总记录数
                    if(null == mappedStatement) {
                        PreparedStatementHandler pHandler = (PreparedStatementHandler) ReflectionUtils.getFieldValue(handler, "delegate");
                        mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(pHandler, "mappedStatement");
                    }
                    Integer total = this.getTotalRecords(invocation, mappedStatement, boundSql, sql, params);
                    page.setTotalRecord(total);
                    page.setTotalPage((total % page.getPageSize() == 0) ? (total / page.getPageSize()) : (total / page.getPageSize() + 1));
                }
                
                if(null == this.dialect) { 
                    String dbType = properties.getProperty("dialect");
                    this.dialect = DialectFactory.getDialect(dbType);
                    if(null == this.dialect) {
                        this.dialect = new MySQLDialect();
                    }
                }

                metaObj.setValue("delegate.boundSql.sql", dialect.getPageSql(boundSql.getSql(), page.getPageNo(), page.getPageSize()));
                metaObj.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaObj.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
            }
        }

        return invocation.proceed();
    }
    
    private Integer getTotalRecords(Invocation invocation, MappedStatement mappedStatement, BoundSql boundSql,
            String sql, Object params) throws SQLException {

        Integer total = null;
        
        String querySql = this.dialect.getCountSql(sql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection connection = (Connection) invocation.getArgs()[0];
            pstmt = connection.prepareStatement(querySql);
            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), querySql, boundSql.getParameterMappings(), params);
            setPreparedStatement(newBoundSql, pstmt, mappedStatement, params);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                total = rs.getInt(1);
            }
        } finally {
            if(null != rs) {
                rs.close();
            }
            if(null != pstmt) {
                pstmt.close();
            }
        }
        
        return total;
    }
    
    /**
     * 给totalSql的条件赋值
     * @param newBoundSql
     * @param pstmt
     * @param mappedStatement
     * @param params
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setPreparedStatement(BoundSql newBoundSql,
            PreparedStatement pstmt, MappedStatement mappedStatement,
            Object params) {
        List<ParameterMapping> parameterMappings = newBoundSql.getParameterMappings();
        if(null != parameterMappings) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = params == null ? null : configuration.newMetaObject(params);
            int i = 0;
            for(ParameterMapping parameterMapping : parameterMappings) {
                if(parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value = null;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if(null == params) {
                        value = null;
                    } else if(typeHandlerRegistry.hasTypeHandler(params.getClass())) {
                        value = params;
                    } else if(newBoundSql.hasAdditionalParameter(propertyName)) {
                        value = newBoundSql.getAdditionalParameter(propertyName);
                    } else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && newBoundSql.hasAdditionalParameter(prop.getName())) {
                        value = newBoundSql.getAdditionalParameter(prop.getName());
                        if(null != value) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)) {
                        String property = propertyName.replace(ForEachSqlNode.ITEM_PREFIX, "");
                        String[] arrays = property.split("_");
                        Map<String, Object> map = (Map<String, Object>) params;
                        List values = (List) map.get(arrays[0]);
                        value = values.get(Integer.parseInt(arrays[1]));
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    try {
                        typeHandler.setParameter(pstmt, i + 1, value, parameterMapping.getJdbcType());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
        if(null == this.dialect) { 
            String dbType = properties.getProperty("dialect");
            this.dialect = DialectFactory.getDialect(dbType);
            if(null == this.dialect) {
                this.dialect = new MySQLDialect();
            }
        }
    }

}
