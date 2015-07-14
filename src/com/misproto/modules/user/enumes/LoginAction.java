package com.misproto.modules.user.enumes;

/****
 * 登陆动作
 * 
 * @author NBQ
 *
 */
public enum LoginAction {

	IN("1", "登录"), OUT("0", "退出");

	private String code;
	private String name;

	private LoginAction(String code, String name) {
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
