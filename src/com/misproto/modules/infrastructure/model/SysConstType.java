package com.misproto.modules.infrastructure.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.misproto.modules.base.BaseModel;

/****
 * 系统的常量类型
 * 
 * @author NBQ
 *
 */
public class SysConstType extends BaseModel {

	private String typecode; // 常量类型码
	private String typename; // 常量类型名

	@NotEmpty(message = "常量类型不能为空")
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	@NotEmpty(message = "常量类型名称不能为空")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}
