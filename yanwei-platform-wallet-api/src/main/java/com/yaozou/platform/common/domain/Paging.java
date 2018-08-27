package com.yaozou.platform.common.domain;

public class Paging implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4418956503672080069L;

    private Integer           totalRow;                               // 数据量

    private Integer           pageSize;                               // 每页最多显示多少条数据

    private Integer           pageIndex;                              // 当前页码

    private Integer           pageCount;                              // 总页数

    public Paging(Integer totalRow, Integer pageSize, Integer pageIndex) {
        this.totalRow = totalRow;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        if (null != totalRow && totalRow > 0 && null != pageSize && pageSize > 0) {
            this.pageCount = (int) (Math.ceil(1.0 * totalRow / pageSize));
        }
    }

    public Integer getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Integer totalRow) {
        this.totalRow = totalRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

}
