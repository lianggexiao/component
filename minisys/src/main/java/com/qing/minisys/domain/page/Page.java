package com.qing.minisys.domain.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分页数据对象，组装分页需要的数据
 * @author wurong
 */
public class Page<T> {

    private int pageNo;
    private int pageSize;
    private int totalRecord;
    private int totalPage;
    private List<T> data;
    private Map<String, Object> map = new HashMap<String, Object>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
    
    public Object getMap(String key) {
        return map.get(key);
    }
    
    public void put(String key, Object object) {
        map.put(key, object);
    }
}
