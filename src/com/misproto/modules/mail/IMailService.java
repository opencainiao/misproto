package com.misproto.modules.mail;

import org.thymeleaf.context.Context;

/**
 * 发送邮件接口
 * 
 * @author NBQ
 *
 */
public interface IMailService {

	/**
	 * 发送 HTML 邮件
	 * 
	 * @param receivers
	 *            多个收件箱地址用 ,分隔
	 * @param subject
	 * @param text
	 */
	public abstract void sendHtmlEmails(String receivers, String subject,
			String content);

	/**
	 * 发送 HTML 邮件，按邮件模板
	 * 
	 * @param receivers
	 * @param subject
	 * @param templatename
	 * @param data
	 */
	public abstract void sendHtmlEmailsByTemplate(String receivers,
			String subject, String templatename, Context data);
}
