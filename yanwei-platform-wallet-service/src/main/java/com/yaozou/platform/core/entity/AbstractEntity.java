package com.yaozou.platform.core.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 3802710266224854053L;

	public abstract Object getId();

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AbstractEntity)
				&& (this.getClass().getName().equals(object.getClass()
						.getName())))
			return false;
		AbstractEntity other = (AbstractEntity) object;
		return getId().equals(other.getId());
	}

	@Override
	public int hashCode() {
		if (null == getId()) {
			return super.hashCode();
		}
		return getId().hashCode();
	}

	public AbstractEntity clone() throws CloneNotSupportedException {
		return (AbstractEntity) super.clone();
	}
}