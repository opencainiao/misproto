package com.misproto.modules.user.enumes;

public enum RegistResult {

	NOTEQUALPWD("01", "两次密码不一致"), EXIST("02", "邮箱已被注册"), SUCCESS("00", "账户注册成功");
	private String code;
	private String name;

	private RegistResult(String code, String name) {
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
