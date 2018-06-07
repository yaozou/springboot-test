package com.yaozou.platform.common.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yaozou.platform.common.domain.SysLogDO;
import com.yaozou.platform.common.repository.CustomRepository;
import com.yaozou.platform.common.service.LogService;


@Service
@Transactional
public class LogServiceImpl implements LogService {

	@Autowired
	private CustomRepository customRepository;
	
	@Override
	public void save(SysLogDO sysLogDO) {
		customRepository.save(sysLogDO);
	}

}
