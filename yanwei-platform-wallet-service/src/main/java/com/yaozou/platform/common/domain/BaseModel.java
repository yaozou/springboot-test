package com.yaozou.platform.common.domain;

import java.util.Date;

import lombok.Data;

/**
 * Created by leo on 2017/11/15.
 */
@Data
public class BaseModel {

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    
}
