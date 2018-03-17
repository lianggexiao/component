package com.qing.minisys.domain.page.dialect;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 方言工厂
 */
public class DialectFactory {

    private static List<Dialect> list = new CopyOnWriteArrayList<Dialect>(); 
    
    private static ConcurrentMap<String, Dialect> factory = new ConcurrentHashMap<String, Dialect>();
    
    static {
        list.add(new MySQLDialect());
    }
    
    /**
     * 获取方言对象实例
     * @param dbType
     * @return
     */
    public static Dialect getDialect(String dbType) {
        Dialect dialect = factory.get(dbType);
        if(null == dialect) {
            for(Dialect d : list) {
                if(d.skip(dbType)) {
                    dialect = d;
                    factory.put(dbType, dialect);
                    break;
                }
            }
        }
        
        return dialect;
    }
}
