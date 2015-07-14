package com.misproto.modules.global.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misproto.common.globalobj.RequestResult;
import com.misproto.common.util.RandomUtil;
import com.misproto.modules.base.BaseController;
import com.misproto.modules.mail.IUserMailService;

/****
 * 注册控制器
 * 
 * @author NBQ
 */
@Controller
@RequestMapping("/usermail")
public class UsermailController extends BaseController {

	@Resource              
	public IUserMailService userMailService = null;

	private static final Logger logger = LogManager
			.getLogger(UsermailController.class);

	/****
	 * 发送用户激活邮件
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ative", method = RequestMethod.GET)
	@ResponseBody
	public Object sendmail(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("发送【激活】邮件");

		RequestResult rr = new RequestResult();

		try {
			String receiver = "25662996@qq.com";
			String activecode = RandomUtil.getRandomEncryptStr();

			Map<String, String> params = createParams();
			params.put("email", receiver);
			params.put("date", DateUtil.getCurdate());
			params.put("activecode", activecode);

			this.userMailService.sendActiveEmail(params, receiver);

			rr.setSuccess(true);
			rr.setMessage("发送成功");
			return rr;
		} catch (Exception e) {
			e.printStackTrace();
			return this.handleException(e);
		}
	}

	/****
	 * 发送用户激活邮件
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	@ResponseBody
	public Object resetpassword(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("发送【重置密码】邮件");

		RequestResult rr = new RequestResult();

		try {
			
			String receiver = "25662996@qq.com";

			Map<String, String> params = createParams();
			params.put("email", receiver);
			params.put("date", DateUtil.getCurdate());
			params.put("userid", "0000123213213210");
			params.put("nickname", "小明");

			this.userMailService.sendResetPasswordEmail(params, receiver);

			rr.setSuccess(true);
			rr.setMessage("发送成功");
			return rr;
		} catch (Exception e) {
			e.printStackTrace();
			return this.handleException(e);
		}
	}

	/****
	 * 发送用户修改密码成功邮件
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/resetpasswordsuccess", method = RequestMethod.GET)
	@ResponseBody
	public Object resetpasswordsuccess(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("发送【重置密码成功】邮件");

		RequestResult rr = new RequestResult();

		try {
			String receiver = "25662996@qq.com";

			Map<String, String> params = createParams();
			params.put("email", receiver);
			params.put("date", DateUtil.getCurdate());
			params.put("userid", "0000123213213210");
			params.put("nickname", "小明");
			
			params.put("optime", DateUtil.getCurrentTimsmpS());
			params.put("opsystem", this.getUserOS());
			params.put("opbrowser", this.getUserBrowser());
			params.put("opip", this.getUserIP());
			
			this.userMailService
					.sendResetPasswordSuccessEmail(params, receiver);

			rr.setSuccess(true);
			rr.setMessage("发送成功");
			return rr;
		} catch (Exception e) {
			e.printStackTrace();
			return this.handleException(e);
		}
	}

	private Map<String, String> createParams() {

		Map<String, String> params = new HashMap<String, String>();

		return params;
	}
}
