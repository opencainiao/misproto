package com.misproto.modules.user.model;

/****
 * 登陆信息
 * 
 * @author NBQ
 *
 */
public class LoginInf {

	// 登陆时间
	private String loginTime;

	// 登陆ip地址
	private String loginIp;

	// 登陆状态
	private String loginState;

	// 登陆状态名称
	private String loginStateName;

	// 退出时间
	private String logoutTime;

	// 最后一次操作时间
	private String lastOpTime;

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginState() {
		return loginState;
	}

	public void setLoginState(String loginState) {
		this.loginState = loginState;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getLastOpTime() {
		return lastOpTime;
	}

	public void setLastOpTime(String lastOpTime) {
		this.lastOpTime = lastOpTime;
	}

	public String getLoginStateName() {
		return loginStateName;
	}

	public void setLoginStateName(String loginStateName) {
		this.loginStateName = loginStateName;
	}

}
