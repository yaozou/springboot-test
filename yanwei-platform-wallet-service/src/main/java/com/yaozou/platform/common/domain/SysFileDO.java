package com.yaozou.platform.common.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

 
/**
 * 文件上传
 * @author luojianhong
 * @version $Id: SysFileDO.java, v 0.1 2017年10月10日 下午2:02:08 luojianhong Exp $
 */
@Data
public class SysFileDO implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private Long id;
	// 文件类型
	private Integer type;
	// URL地址
	private String url;
	// 创建时间
	private Date createDate;

	public SysFileDO() {
		super();
	}

	public SysFileDO(Integer type, String url, Date createDate) {
		super();
		this.type = type;
		this.url = url;
		this.createDate = createDate;
	}
	
}
