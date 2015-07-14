package com.misproto.modules.mail;

import java.util.Map;

/**
 * 给用户发送邮件接口
 * 
 * @author NBQ
 *
 */
public interface IUserMailService {

	/****
	 * 发送用户激活邮件(用户注册成功后，发送用户邮件通知)
	 * 
	 * @param params
	 * @param receiver
	 */
	public abstract void sendActiveEmail(Map<String, String> params,
			String receiver);

	/****
	 * 发送用户重置密码(当用户忘记密码后，发送用户邮件重置密码通知)
	 * 
	 * @param params
	 * @param receiver
	 */
	public abstract void sendResetPasswordEmail(Map<String, String> params,
			String receiver);

	/****
	 * 发送用户重置密码成功(当用户重置密码后，发送用户邮件重置密码成功通知)
	 * 
	 * @param params
	 * @param receiver
	 */
	public abstract void sendResetPasswordSuccessEmail(
			Map<String, String> params, String receiver);

}
