package com.misproto.modules.user.enumes;

/****
 * 用户邮件配置
 * 
 * @author NBQ
 *
 */
public enum UserMailConfig {

	USER_ACTIVE("请激活您的 BXB 账户！", "user_active.html"), //
	RESET_PASSWORD("重设 BXB 密码", "reset_password.html"), //
	RESET_PASSWORD_SUCCESS("BXB 密码已被修改", "reset_password_success.html"); //

	private String subject;
	private String templatename;

	private UserMailConfig(String subject, String templatename) {
		this.subject = subject;
		this.templatename = templatename;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTemplatename() {
		return templatename;
	}

	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}

}
