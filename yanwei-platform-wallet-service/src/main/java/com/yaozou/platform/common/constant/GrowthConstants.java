package com.yaozou.platform.common.constant;

/**
 * 成长值　常量记录表
 * @author luojianhong
 * @version $Id: GrowthConstants.java, v 0.1 2017年11月17日 上午10:59:00 luojianhong Exp $
 */
public abstract class GrowthConstants {

    /***
     * 成长值加
     */
    public final static int GROWTH_ADD                   = 0;

    /***
     *成长值1减
     */
    public final static int GROWTH_SUB                   = 1;

    /***
     * 变动原因(1签到 ,2购买商品,3发布评价,4发布晒单,5 转发文章 6,成长值到期7.返还)
     * 1签到
     */
    public final static int GROWTH_REASON_SIGN           = 1;
    
    /***
     *签到描述
     */
    public final static String GROWTH_REASON_SIGN_DESC    = "每日签到";
    /***
     *签到描述
     */
    public final static String GROWTH_REASON_ARICLE_DESC    = "转发文章";
    /***
     *发布评价
     */
    public final static String GROWTH_REASON_PRDOUCT_DESC    = "发布评价";
    /***
     *发布晒单
     */
    public final static String GROWTH_REASON_PRDOUCT_SUN_DESC    = "发布晒单";
    /***
     * 购买商品
     */
    public final static int GROWTH_REASON_BUY_GOOD       = 2;
    /***
     * 发布评价
     */
    public final static int GROWTH_REASON_COMMENT        = 3;
    /****
     * 发布晒单
     */
    public final static int GROWTH_REASON_COMMENT_SUN    = 4;
    /**
     * 转发文章 
     */
    public final static int GROWTH_REASON_FORWARD_ARICLE = 5;
    /***
     * 成长值到期 减少
     */
    public final static int GROWTH_REASON_EXPIRY_TIME    = 6;
    /***
     * 退商品
     */
    public final static int GROWTH_REASON_BACK_GOOD      = 7;
    /***
     * 系统扣减
     */
    public final static int GROWTH_REASON_SYSTEM         = 8;

}
