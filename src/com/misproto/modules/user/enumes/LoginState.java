package com.misproto.modules.user.enumes;

public enum LoginState {

	IN("1", "已登录"), NOTIN("0", "未登录");

	private String code;
	private String name;

	private LoginState(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
