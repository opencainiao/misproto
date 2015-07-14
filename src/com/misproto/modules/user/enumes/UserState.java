package com.misproto.modules.user.enumes;

/****
 * 用户状态类型
 * 
 * @author NBQ
 *
 */
public enum UserState {

	NOTACTIVE("0", "未激活"), //
	ACTIVE("1", "正常状态"), //
	FRIZED("2", "已冻结"), //
	DELETED("3", "已删除");

	private String code;
	private String name;

	public static UserState getUserStateByCode(String code) {

		UserState rtnUserState = null;

		if (code.equals(UserState.NOTACTIVE.getCode())) {
			rtnUserState = UserState.NOTACTIVE;
		} else if (code.equals(UserState.ACTIVE.getCode())) {
			rtnUserState = UserState.ACTIVE;
		} else if (code.equals(UserState.FRIZED.getCode())) {
			rtnUserState = UserState.FRIZED;
		} else if (code.equals(UserState.DELETED.getCode())) {
			rtnUserState = UserState.DELETED;
		}

		return rtnUserState;
	}

	private UserState(String code, String name) {
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
