package com.misproto.modules.infrastructure.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.misproto.modules.base.BaseModel;

/****
 * 系统常量类
 * 
 * @author NBQ
 *
 */
public class SysConst extends BaseModel {

	private String typecode; // 常量类型
	private String typename; // 常量类型名称
	private String val;// 常量值
	private String dspval; // 常量显示值
	private String valordernum;// 常量值顺序号

	@NotEmpty(message = "常量类型不能为空")
	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	@NotEmpty(message = "常量类型不能为空")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@NotEmpty(message = "常量值不能为空")
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@NotEmpty(message = "常量显示值不能为空")
	public String getDspval() {
		return dspval;
	}

	public void setDspval(String dspval) {
		this.dspval = dspval;
	}

	public String getValordernum() {
		return valordernum;
	}

	public void setValordernum(String valordernum) {
		this.valordernum = valordernum;
	}

}
