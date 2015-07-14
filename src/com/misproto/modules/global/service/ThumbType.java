package com.misproto.modules.global.service;

/****
 * 用户状态类型
 * 
 * @author NBQ
 *
 */
public enum ThumbType {

	COMPRESS_W_H_INNER("0", "压缩到指定大小范围内"), COMPRESS_CAIJIAN("1", "压缩裁剪"), NO_COMPRESS_CAIJIAN(
			"2", "不进行任何压缩直接裁剪");

	private String code;
	private String name;

	private ThumbType(String code, String name) {
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
