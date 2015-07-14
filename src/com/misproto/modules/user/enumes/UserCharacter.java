package com.misproto.modules.user.enumes;

/****
 * 用户性质
 * 
 * @author NBQ
 *
 */
public enum UserCharacter {

	BACKEND_MANAGER_USER("0", "后台管理用户"), //
	NORMAL_REGIST_USER("1", "前端注册用户"), //
	INVALID_USER("2", "无效用户");

	private String code;
	private String name;

	public static UserCharacter getUserCharacterByCode(String code) {

		UserCharacter rtnUserCharacter = null;

		if (code.equals(UserCharacter.BACKEND_MANAGER_USER.getCode())) {
			rtnUserCharacter = UserCharacter.BACKEND_MANAGER_USER;
		} else if (code.equals(UserCharacter.NORMAL_REGIST_USER.getCode())) {
			rtnUserCharacter = UserCharacter.NORMAL_REGIST_USER;
		} else {
			rtnUserCharacter = UserCharacter.INVALID_USER;
		}

		return rtnUserCharacter;
	}

	private UserCharacter(String code, String name) {
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
