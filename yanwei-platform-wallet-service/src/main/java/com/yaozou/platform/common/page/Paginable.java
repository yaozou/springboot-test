package com.yaozou.platform.common.page;

/**
 *  分页接口
 * @author luojianhong
 * @version $Id: Paginable.java, v 0.1 2017年7月14日 下午2:32:47 luojianhong Exp $
 */
public interface Paginable {
    /**
     * 总记录数
     * 
     * @return
     */
    public int getTotalCount();

    /**
     * 总页数
     * 
     * @return
     */
    public int getTotalPage();

    /**
     * 每页记录数
     * 
     * @return
     */
    public int getPageSize();

    /**
     * 当前页号
     * 
     * @return
     */
    public int getPageNo();

    /**
     * 是否第一页
     * 
     * @return
     */
    public boolean isFirstPage();

    /**
     * 是否最后一页
     * 
     * @return
     */
    public boolean isLastPage();

    /**
     * 返回下页的页号
     */
    public int getNextPage();

    /**
     * 返回上页的页号
     */
    public int getPrePage();
}
