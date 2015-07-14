package com.misproto.modules.user.model;

import mou.mongodb.MongoObjectId;

/****
 * 用户登陆信息
 * 
 * @author NBQ
 *
 */
public class LoginUserInf {
	
	private MongoObjectId _id;// mongo主键

	// 冗余字段(用户名)
	private String username;

	// 冗余字段(邮箱)
	private String email;

	// 用户信息
	private User user;

	// 登陆信息
	private LoginInf loginInf;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		
		if (user != null){
			this.username = user.getUsername();
			this.email = user.getEmail();
		}
		this.user = user;
	}

	public LoginInf getLoginInf() {
		return loginInf;
	}

	public void setLoginInf(LoginInf loginInf) {
		this.loginInf = loginInf;
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
