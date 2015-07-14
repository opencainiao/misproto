package com.misproto.modules.user.enumes;

/****
 * 用户激活的返回结果
 * 
 * @author NBQ
 *
 */
public enum UserActiveResult {

	ACTIVECODEERROR("01", "激活码错误"), //
	TIMEOUT("02", "链接已失效"), //
	SUCCESS("00", "账户激活成功");
	
	private String code;
	private String name;

	private UserActiveResult(String code, String name) {
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
