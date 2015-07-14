package com.misproto.modules.customer.model;

import java.util.List;

import mou.mongodb.MongoObjectId;

public class Customer {

	private MongoObjectId _id;// mongo主键

	private String name;// 姓名
	private String idnumber; // 身份证号
	private String sex; // 性别
	private String age; // 年龄
	private String mainemail;// 用户的主要邮件
	private String mainphone;// 用户的主要联系电话
	private String mainworkphone;// 用户的主要办公电话

	private List<Phone> phones;
	private List<Email> emails;
	private List<Address> address;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMainemail() {
		return mainemail;
	}

	public void setMainemail(String mainemail) {
		this.mainemail = mainemail;
	}

	public String getMainphone() {
		return mainphone;
	}

	public void setMainphone(String mainphone) {
		this.mainphone = mainphone;
	}

	public String getMainworkphone() {
		return mainworkphone;
	}

	public void setMainworkphone(String mainworkphone) {
		this.mainworkphone = mainworkphone;
	}

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public String get_id_str() {
		return _id.toString();
	}

	public MongoObjectId get_id() {
		return _id;
	}

	public void set_id(MongoObjectId _id) {
		this._id = _id;
	}

	public void set_id(String _id) {
		MongoObjectId _idNew = new MongoObjectId();
		_idNew.set$oid(_id);
		this._id = _idNew;
	}
}
