package com.misproto.modules.mail;

import java.util.Map;

import javax.annotation.Resource;

import mou.mongodb.util.JsonUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.misproto.modules.user.enumes.UserMailConfig;

@Service("userMailService")
public class UserMailServiceImpl implements IUserMailService {

	private static final Logger logger = LogManager
			.getLogger(UserMailServiceImpl.class);

	@Resource
	public IMailService mailService = null;

	@Override
	public void sendActiveEmail(Map<String, String> params, String receiver) {

		Context data = new Context();
		data.setVariable("email", params.get("email"));
		data.setVariable("date", params.get("date"));
		data.setVariable("activecode", params.get("activecode"));

		logger.debug("发送激活邮件，发送的参数为\n{}", JsonUtil.toJsonStr(params));
		
		this.mailService.sendHtmlEmailsByTemplate(receiver,
				UserMailConfig.USER_ACTIVE.getSubject(),
				UserMailConfig.USER_ACTIVE.getTemplatename(), data);
	}

	@Override
	public void sendResetPasswordEmail(Map<String, String> params,
			String receiver) {
		Context data = new Context();
		data.setVariable("resetpwdcode", params.get("resetpwdcode"));
		data.setVariable("nickname", params.get("nickname"));
		data.setVariable("email", params.get("email"));
		data.setVariable("date", params.get("date"));

		logger.debug("发送重置密码邮件，发送的参数为\n{}", JsonUtil.toJsonStr(params));

		this.mailService.sendHtmlEmailsByTemplate(receiver,
				UserMailConfig.RESET_PASSWORD.getSubject(),
				UserMailConfig.RESET_PASSWORD.getTemplatename(), data);
	}

	@Override
	public void sendResetPasswordSuccessEmail(Map<String, String> params,
			String receiver) {

		Context data = new Context();
		data.setVariable("nickname", params.get("nickname"));
		data.setVariable("email", params.get("email"));
		data.setVariable("date", params.get("date"));
		data.setVariable("optime", params.get("optime"));
		data.setVariable("opsystem", params.get("opsystem"));
		data.setVariable("opbrowser", params.get("opbrowser"));
		data.setVariable("opip", params.get("opip"));
		
		this.mailService.sendHtmlEmailsByTemplate(receiver,
				UserMailConfig.RESET_PASSWORD_SUCCESS.getSubject(),
				UserMailConfig.RESET_PASSWORD_SUCCESS.getTemplatename(), data);
	}

}
