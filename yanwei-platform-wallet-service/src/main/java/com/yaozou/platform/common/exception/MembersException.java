package com.yanwei.platform.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class MembersException extends RuntimeException {
	private static final long serialVersionUID = -4523452403295843206L;
	public int code;

    public MembersException(String msg) {
        super(msg);
    }
}
