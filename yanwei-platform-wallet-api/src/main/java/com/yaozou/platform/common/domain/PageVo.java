package com.yaozou.platform.common.domain;

import lombok.Data;

/**
 * Created by leo on 2017/11/14.
 */
@Data
public class PageVo {

    private Integer pageSize=20;  // 每页最多显示多少条数据

    private Integer pageIndex=1; // 当前页码

}
