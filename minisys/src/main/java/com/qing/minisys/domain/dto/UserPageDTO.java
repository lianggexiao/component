package com.qing.minisys.domain.dto;

import com.qing.minisys.domain.RequestDTO;

public class UserPageDTO extends RequestDTO {

    private String name;
    private int pageNo;
    private int pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
