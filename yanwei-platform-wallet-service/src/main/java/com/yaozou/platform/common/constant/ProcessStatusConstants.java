package com.yaozou.platform.common.constant;

/**
 * 处理状态常量表
 * 处理状态(0.待处理 1已处理 2.处理异常 3.删除)
 * @author luojianhong
 * @version $Id: ProcessStatusConstants.java, v 0.1 2017年11月17日 上午9:21:43 luojianhong Exp $
 */
public abstract class ProcessStatusConstants {

    /***
     * 待处理
     */
    public final static int PROCESS_PENDING = 0;

    /***
     * 已处理
     */
    public final static int PROCESS_PASS    = 1;

    /***
     * 处理异常
     */
    public final static int PROCESS_ERROR   = 2;

    /***
     * 删除
     */
    public final static int PROCESS_DELETE  = 3;

}
