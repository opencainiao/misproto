package com.misproto.common.globalobj;

import java.util.Map;

public class RequestResult {

	private String success; // y-成功,n-失败
	private String message; // 消息
	private Map<String, String> errors;
	private Map<String, String> brErrors;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		if (success){
			this.success = "y";
		}else{
			this.success = "n";
		}
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
		this.success = "n";
	}

	public Map<String, String> getBrErrors() {
		return brErrors;
	}

	public void setBrErrors(Map<String, String> brErrors) {
		this.brErrors = brErrors;
		this.success = "n";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
