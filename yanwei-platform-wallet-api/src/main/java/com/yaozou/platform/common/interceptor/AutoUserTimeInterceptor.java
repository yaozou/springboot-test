package com.yaozou.platform.common.interceptor;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import com.yaozou.platform.common.domain.TokenModel;
import com.yaozou.platform.common.utils.ConvertUtils;
import com.yaozou.platform.common.utils.HttpContextUtils;

/**
 * entity - POJO对象
 * 
 * @author luojianhong
 * @version $Id: AutoUpdateTimeInterceptor.java, v 0.1 2017年7月10日 下午3:59:51
 *          luojianhong Exp $
 */
@Component
public class AutoUserTimeInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -8597658125309889388L;

	/*
	 * entity - POJO对象 id - POJO对象的主键 state - POJO对象的每一个属性所组成的集合(除了ID)
	 * propertyNames - POJO对象的每一个属性名字组成的集合(除了ID) types -
	 * POJO对象的每一个属性类型所对应的Hibernate类型组成的集合(除了ID)
	 */
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		TokenModel tokenModel = HttpContextUtils.getTokenModel();
		// 添加数据
		for (int index = 0; index < propertyNames.length; index++) {
			/* 找到名为"创建时间"的属性 */
			if (DataBaseConstant.CREATE_DATE.equals(propertyNames[index])
					|| DataBaseConstant.CREATE_TIME
							.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"创建时间"属性赋上值 */
				if (ConvertUtils.isEmpty(state[index])) {
					state[index] = new Date();
				}
				continue;
			}
			/* 找到名为"创建人名称"的属性 */
			else if (DataBaseConstant.CREATE_NAME.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"创建人名称"属性赋上值 */
				if (tokenModel != null && ConvertUtils.isEmpty(state[index])) {
					state[index] = tokenModel.getUserName();
				}
				continue;
			}
			/* 找到名为"token用户ID"的属性 */
			else if (DataBaseConstant.USER_ID.equals(propertyNames[index])
					|| DataBaseConstant.USER_ID_TABLE
							.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"创建人名称"属性赋上值 */
				if (tokenModel != null && ConvertUtils.isEmpty(state[index])) {
					state[index] = tokenModel.getUserId();
				}
				continue;
			}
			/* 找到名为"token用户名称""的属性 */
			else if (DataBaseConstant.USER_NAME.equals(propertyNames[index])
					|| DataBaseConstant.USER_NAME_TABLE
							.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"创建人名称"属性赋上值 */
				if (tokenModel != null && ConvertUtils.isEmpty(state[index])) {
					state[index] = tokenModel.getUserName();
				}
				continue;
			}
			/* 找到名为"有效期"的属性 */
			else if (DataBaseConstant.EXPIRY_DATE.equals(propertyNames[index])
					|| DataBaseConstant.EXPIRY_DATE_TABLE
							.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"有效期"属性赋上值 */
				if (ConvertUtils.isEmpty(state[index])) {
					state[index] = new Date();
				}
				continue;
			}
			/* 找到名为"有效期时间"的属性 */
			else if (DataBaseConstant.EXPIRY_TIME.equals(propertyNames[index])
					|| DataBaseConstant.EXPIRY_TIME_TABLE
							.equals(propertyNames[index])) {
				/* 使用拦截器将对象的"有效期时间"属性赋上值 */
				if (ConvertUtils.isEmpty(state[index])) {
					state[index] = new Date();
				}
				continue;
			}
		}
		return true;
	}

	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] state, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		try {
			TokenModel tokenModel = HttpContextUtils.getTokenModel();
			for (int index = 0; index < propertyNames.length; index++) {
				if (DataBaseConstant.UPDATE_DATE.equals(propertyNames[index])
						|| DataBaseConstant.UPDATE_TIME
								.equals(propertyNames[index])) {
					/* 使用拦截器将对象的"更新时间"属性赋上值 */
					if (ConvertUtils.isEmpty(state[index])) {
						state[index] = new Date();
					}
					continue;
				}
				else if (DataBaseConstant.UPDATE_NAME.equals(propertyNames[index])
						|| DataBaseConstant.UPDATE_NAME_TABLE
								.equals(propertyNames[index])) {
					/* 使用拦截器将对象的"修改人"属性赋上值 */
					if (tokenModel != null && ConvertUtils.isEmpty(state[index])) {
						state[index] = tokenModel.getUserName();
					}
					continue;
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}