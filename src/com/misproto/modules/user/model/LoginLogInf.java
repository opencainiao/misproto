package com.misproto.modules.user.model;

/****
 * 登陆日志信息
 * 
 * @author NBQ
 *
 */
public class LoginLogInf {

	private String logId;

	// 用户名
	private String username;

	// 操作码
	private String actioncode;

	// 操作名
	private String actionName;

	// ip地址
	private String ip;

	// 操作时间
	private String opTime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getActioncode() {
		return actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOpTime() {
		return opTime;
	}

	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

}
