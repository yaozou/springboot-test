package com.yaozou.platform.common.domain;
import lombok.Data;

/**
 * 确认收货
 * @author luojh
 * @version $Id: MemberConfirmationOrderVo.java, v 0.1 2017年12月10日 下午1:53:46 luojh Exp $
 */
@Data
public class MemberConfirmationOrderVo implements java.io.Serializable {
    
    /**  */
    private static final long serialVersionUID = 1L;

    //订单商品编号
    private String orderNum;
    
    //确认收货时间
    private String confirmDate;
    //
    private String userId;

}
