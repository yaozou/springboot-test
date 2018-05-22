package com.yanwei.platform.common.page;

import java.util.List;

/**
 * 列表分页。包含list属性。
 * @author luojianhong
 * @version $Id: Pagination.java, v 0.1 2017年7月14日 下午2:36:11 luojianhong Exp $
 */
@SuppressWarnings("serial")
public class Pagination<T> extends SimplePage implements java.io.Serializable, Paginable {

    public Pagination() {
    }

    /**
     * 构造器
     * 
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalCount
     *            总共几条数据
     */
    public Pagination(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

    /**
     * 构造器
     * @param <T>
     * 
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页几条数据
     * @param totalCount
     *            总共几条数据
     * @param list
     *            分页内容
     */
    public Pagination(int pageNo, int pageSize, int totalCount, List<T> list) {
        super(pageNo, pageSize, totalCount);
        this.list = list;
    }

    /**
     * 第一条数据位置
     * 
     * @return
     */
    public int getFirstResult() {
       
        return (pageNo - 1) * pageSize;
    }

    /**
     * 当前页的数据
     */
    private List<T> list;

    /**
     * 获得分页内容
     * 
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置分页内容
     * @param <T>
     * 
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }
}
