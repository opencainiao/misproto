package com.misproto.modules.infrastructure.model;

import com.misproto.modules.base.BaseModel;

/****
 * 系统标签，标示标签的值信息
 * 
 * @author NBQ
 *
 */
public class Tag extends BaseModel {

	private String typeid; // 标签类型id
	private String typename;// 标签类型名称
	private String tagcode; // 标签码
	private String tagname; // 标签名
	private String tagcharacter; // 标签属性 1-系统内置 2-用户自定义

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTagcode() {
		return tagcode;
	}

	public void setTagcode(String tagcode) {
		this.tagcode = tagcode;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getTagcharacter() {
		return tagcharacter;
	}

	public void setTagcharacter(String tagcharacter) {
		this.tagcharacter = tagcharacter;
	}
}
