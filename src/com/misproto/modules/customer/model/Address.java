package com.misproto.modules.customer.model;

import com.misproto.modules.base.BaseModel;
import com.misproto.modules.infrastructure.model.Tag;

public class Address extends BaseModel {

	private String address;
	private String mainflg;
	private Tag tag;
	private String ownerid;
	private String name;
	private String idnumber;
	private String age;
	private String sex;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMainflg() {
		return mainflg;
	}

	public void setMainflg(String mainflg) {
		this.mainflg = mainflg;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}