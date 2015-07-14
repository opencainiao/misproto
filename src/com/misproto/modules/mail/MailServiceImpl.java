/*
 *    Copyright 2012-2013 The Haohui Network Corporation
 */
package com.misproto.modules.mail;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 封装 Spring 集成的邮件发送服务实现类
 * 
 * 支持异步发送 利用Spring框架封装的JavaMail现实同步或异步邮件发送 <br>
 * spring 会负责每次发送后正确关闭 transport
 * 
 */
@Service("mailService")
public class MailServiceImpl implements IMailService {

	private static final Logger logger = LogManager
			.getLogger(MailServiceImpl.class);

	@Resource
	JavaMailSender mailSender;// 注入Spring封装的javamail，Spring的xml中已让框架装配
	@Resource
	TaskExecutor taskExecutor;// 注入Spring封装的异步执行器
	@Resource
	private TemplateEngine templateEngine;

	/**
	 * 发送邮件的具体实现, 目前是异步发送
	 */
	public void sendHtmlEmails(String receivers, String subject, String content) {
		this.sendMailByAsynchronousMode(receivers, subject, content);
	}

	/**
	 * 异步发送
	 * 
	 * @see com.misproto.modules.mail.IMailService.service.MailService#sendMailByAsynchronousMode(com.zhangjihao.bean.Email)
	 */
	public void sendMailByAsynchronousMode(final String receivers,
			final String subject, final String content) {
		if (logger.isDebugEnabled()) {
			logger.debug("当前邮件采取异步发送..");
		}
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					sendMailBySynchronizationMode(receivers, subject, content);
					logger.info("邮件发送耗时任务完成");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	/**
	 * 同步发送
	 * 
	 * @throws IOException
	 */
	public void sendMailBySynchronizationMode(String receivers, String subject,
			String content) throws Exception {
		if (receivers == null) {
			throw new IllegalArgumentException("收件人不能为空");
		}
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,
				"utf-8");

		try {
			receivers = receivers.replaceAll("\\;", ",");
			// 设置收件人，寄件人
			InternetAddress[] toAddress = InternetAddress.parse(receivers);
			mailMessage.setRecipients(Message.RecipientType.TO, toAddress);
			// 发送给多个账号
			messageHelper.setFrom("umctest@126.com");
			messageHelper.setSubject(subject); // 主题
			// true 表示启动HTML格式的邮件
			messageHelper.setText(content, true); // 邮件内容，注意加参数true，表示启用html格式
			
			logger.debug("开始发送邮件");
			// 发送邮件
			mailSender.send(mailMessage);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 或者直接使用 spring 3.0 的异步框架 只需使用 @Async 注解即可。 需要激活 <!-- 注解异步任务驱动 -->
	 * <task:annotation-driven/> 详细配置请见： spring-mail.xml
	 */
	@Async
	public void sendAsync() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.umc.common.mail.MailService#sendHtmlEmailsByTemplate(java.lang.String
	 * , java.lang.String, java.lang.String, org.thymeleaf.context.Context)
	 */
	@Override
	public void sendHtmlEmailsByTemplate(String receivers, String subject,
			String templatename, Context data) {

		String content = templateEngine.process(templatename, data);
		String str1 = content.replaceAll("<!\\[CDATA\\[", "");
		String handledContent = str1.replaceAll("]]>", "");

		logger.debug("待发送的邮件内容为\n{}", handledContent);
		this.sendMailByAsynchronousMode(receivers, subject, handledContent);
	}
}
