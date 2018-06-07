package com.yaozou.platform.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 字典表
 * @author luojianhong
 * @version $Id: SysDictDO.java, v 0.1 2017年10月10日 下午2:01:08 luojianhong Exp $
 */
@Data
public class SysDictDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//标签名
	private String name;
	//数据值
	private String value;
	//类型
	private String type;
	//描述
	private String description;
	//排序（升序）
	private BigDecimal sort;
	//父级编号
	private Long parentId;
	//创建者
	private Integer createBy;
	//创建时间
	private Date createDate;
	//更新者
	private Long updateBy;
	//更新时间
	private Date updateDate;
	//备注信息
	private String remarks;
	//删除标记
	private String delFlag;

}
