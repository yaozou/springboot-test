package com.yaozou.platform.common.domain;

import java.io.Serializable;

import lombok.Data;

/**
 *  table="sys_config_charge"
 */
@Data
public class BootCmsConfigContentCharge implements Serializable {

    /**  */
    private static final long serialVersionUID      = 1L;

    public static String      REF                   = "CmsConfigContentCharge";
    public static String      PROP_WEIXIN_PASSWORD  = "weixinPassword";
    public static String      PROP_SETTLEMENT_DATE  = "settlementDate";
    public static String      PROP_WEIXIN_APP_ID    = "weixinAppId";
    public static String      PROP_CHARGE_RATIO     = "chargeRatio";
    public static String      PROP_COMMISSION_MONTH = "commissionMonth";
    public static String      PROP_ENABLE           = "enable";
    public static String      PROP_COMMISSION_DAY   = "commissionDay";
    public static String      PROP_COMMISSION_YEAR  = "commissionYear";
    public static String      PROP_ID               = "id";
    public static String      PROP_COMMISSION_TOTAL = "commissionTotal";
    public static String      PROP_WEIXIN_ACCOUNT   = "weixinAccount";

    public BootCmsConfigContentCharge() {

    }

    public BootCmsConfigContentCharge(java.lang.Integer id) {
        this.setId(id);
    }

    public BootCmsConfigContentCharge(Integer id, String weixinAppId, String weixinAccount,
                                      String weixinPassword, String alipayAppid,
                                      String alipayAccount, String alipayKey, Double chargeRatio,
                                      Double minDrawAmount) {
        super();
        this.id = id;
        this.weixinAppId = weixinAppId;
        this.weixinAccount = weixinAccount;
        this.weixinPassword = weixinPassword;
        this.alipayAccount = alipayAccount;
        this.alipayKey = alipayKey;
        this.chargeRatio = chargeRatio;
        this.minDrawAmount = minDrawAmount;
    }

    // primary key
    private java.lang.Integer id;

    // fields
    private java.lang.String  weixinAppId;
    private java.lang.String  weixinSecret;
    private java.lang.String  weixinAccount;
    private java.lang.String  weixinPassword;
    private java.lang.String  alipayPartnerId;
    private java.lang.String  alipayAccount;
    private java.lang.String  alipayKey;

    private java.lang.String  alipayAppId;
    private java.lang.String  alipayPublicKey;
    private java.lang.String  alipayPrivateKey;

    private java.lang.Double  chargeRatio;
    private java.lang.Double  minDrawAmount;
    private java.lang.Double  commissionTotal;
    private java.lang.Double  commissionYear;
    private java.lang.Double  commissionMonth;
    private java.lang.Double  commissionDay;
    private java.util.Date    lastBuyTime;
    private java.lang.String  payTransferPassword;
    private java.lang.String  transferApiPassword;
    private java.lang.Double  rewardMin;
    private java.lang.Double  rewardMax;
    private java.lang.Boolean rewardPattern;

}