package com.yaozou.platform.common.domain;

import java.util.Date;
import com.yaozou.platform.common.utils.DateFormatUtil;
import lombok.Data;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author luojianhong
 * @version $Id: TokenModel.java, v 0.1 2017年11月15日 下午3:04:58 luojianhong Exp $
 */
@Data
public class TokenModel {

    //用户id
    private String userId;
    //用户名
    private String userName;
    //随机生成的uuid
    private String token;
    //token失效时间
    private String expires;
    //token失效时间
    private long   expires_in;
    //商家ID;
    private String merchantSid;
    //店铺ID
    private String shopSid;
    
    //用户类型 Admin(1, "系统管理员"), Business(11, "商家端(管理员)"), Client(12, "移动端(APP端及作家)"), Taiping(13, "太平SDK");
    private String userType;
    
   /* public TokenModel(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }*/
    
    public TokenModel(String userId, String userName,String token) {
        this.userId = userId;
        this.userName = userName;
        this.token=token;
    }

    public TokenModel(String userId, String userName, String token, String millise) {
        this.userId = userId;
        this.token = token;
        this.userName = userName;
        this.expires = DateFormatUtil.add(new Date(), "yyyy-MM-dd HH:mm:ss", Long.valueOf(millise));
        this.expires_in = DateFormatUtil.addLong(new Date(), "yyyy-MM-dd HH:mm:ss",Long.valueOf(millise));
    }

}