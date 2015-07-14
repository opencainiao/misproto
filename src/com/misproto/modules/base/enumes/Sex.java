package com.misproto.modules.base.enumes;

/****
 * 性别
 * 
 * @author NBQ
 *
 */
public enum Sex {

	WOMAN("0", "女"), //
	MAN("1", "男"); //

	private String code;
	private String name;

	public static Sex getSexByCode(String code) {

		Sex rtnSex = null;

		if (Sex.WOMAN.getCode().equals(code)) {
			rtnSex = Sex.WOMAN;
		} else {
			rtnSex = Sex.MAN;
		}

		return rtnSex;
	}

	private Sex(String code, String name) {
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
