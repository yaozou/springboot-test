package com.yanwei.platform.common.constant;

/**
 * 审批状态常量
 * @author luojianhong
 * @version $Id: ApprovalConstants.java, v 0.1 2017年11月16日 上午10:10:10 luojianhong Exp $
 */
public abstract class ApprovalConstants {

    /***
     * 待审核
     */
    public final static int APPROVAL_PENDING = 0;
    public final static String APPROVAL_PENDING_DESC = "待审核";
    
    /***
     * 审核通过
     */
    public final static int APPROVAL_PASS    = 1;
    public final static String APPROVAL_PASS_DESC = "审核通过";
    
    /***
     * 审核拒绝
     */
    public final static int APPROVAL_REJECT  = 2;
    public final static String APPROVAL_REJECT_DESC = "审核拒绝";
    
    /***
     * 删除
     */
    public final static int APPROVAL_DELETE  = 3;
    public final static String APPROVAL_DELETE_DESC = "删除";
    
}
