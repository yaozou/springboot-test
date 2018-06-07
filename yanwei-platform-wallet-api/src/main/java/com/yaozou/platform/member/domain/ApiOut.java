package com.yaozou.platform.member.domain;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.yaozou.platform.common.page.Pagination;

public class ApiOut<T> implements java.io.Serializable {

	private static final long serialVersionUID = 8037950438431120229L;

	private static final Integer SUCCESS_CODE = 200;

	private static final Integer FAILURE_CODE = 501; // 一般性错误

	private static final String SUCCESS = "操作成功";

	private static final String FAIL = "操作失败";

	private String state;

	private Integer code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String debugMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long elapsedTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Paging pagination;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	@SuppressWarnings("unchecked")
	public static <T> ApiOut<T> success() {
		return (ApiOut<T>) success(SUCCESS);
	}

	public static <T> ApiOut<T> success(T data) {
		return new ApiOut<>(SUCCESS_CODE, SUCCESS, data);
	}

	@SuppressWarnings("unchecked")
	public static <T> ApiOut<T> success(
			@SuppressWarnings("rawtypes") Pagination data) {
		@SuppressWarnings("rawtypes")
		ApiOut out = ApiOut.success(data.getList());
		out.toPaging(data);
		return out;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ApiOut<T> success(
			@SuppressWarnings("rawtypes") PageInfo data , List<T> list) {
		@SuppressWarnings("rawtypes")
		ApiOut out = ApiOut.success(list);
		out.toPaging(data);
		return out;
	}

	@SuppressWarnings("unchecked")
	public static <T> ApiOut<T> success(
			@SuppressWarnings("rawtypes") PageInfo page , Map data) {
		@SuppressWarnings("rawtypes")
		ApiOut out = ApiOut.success(data);
		out.toPaging(page);
		return out;
	}

	public static <T> ApiOut<T> failure() {
		return failure(FAILURE_CODE, FAIL);
	}

	public static <T> ApiOut<T> failure(String msg) {
		return failure(FAILURE_CODE, msg);
	}

	public static <T> ApiOut<T> failure(Integer code, String msg) {
		return new ApiOut<>(code, msg, null);
	}

	public String getState() {
		if (code != null && code == 200)
			state = SUCCESS;
		else {
			state = FAIL;
		}
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public Paging getPagination() {
		return pagination;
	}

	public void setPagination(Paging pagination) {
		this.pagination = pagination;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiOut(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	private void toPaging(@SuppressWarnings("rawtypes") Pagination pagination) {
		Paging paging = new Paging(pagination.getTotalCount(),
				pagination.getPageSize(), pagination.getPageNo());
		setPagination(paging);
	}
	
	private void toPaging(@SuppressWarnings("rawtypes") PageInfo pageInfo) {
		Paging paging = new Paging(Long.valueOf(pageInfo.getTotal()).intValue(),
				pageInfo.getPageSize(), pageInfo.getPageNum());
		setPagination(paging);
	}

	@SuppressWarnings("unused")
	private ApiOut() {
	}
}
