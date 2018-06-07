package com.yaozou.platform;

import com.yaozou.platform.common.config.BootMemebersConfig;
import com.yaozou.platform.common.utils.SpringContextHolder;

public class ConfigUtils {
	static BootMemebersConfig bootMemebersConfig;
	public static String APP_DOMAIN(){
		if (bootMemebersConfig == null) {
			bootMemebersConfig = SpringContextHolder.getBean("bootMemebersConfig");
		}
		return bootMemebersConfig.getAppDomain();
	}
}
