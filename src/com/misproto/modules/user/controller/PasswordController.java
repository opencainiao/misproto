package com.misproto.modules.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.StringUtil;
import org.mou.common.security.EncryptMou;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.misproto.common.globalobj.RequestResult;
import com.misproto.modules.base.BaseController;
import com.misproto.modules.mail.IUserMailService;
import com.misproto.modules.user.model.User;
import com.misproto.modules.user.service.IUserService;

/****
 * 密码管理控制器
 * 
 * @author NBQ
 */
@Controller
public class PasswordController extends BaseController {

	@Resource
	public IUserMailService userMailService = null;

	@Resource(name = "userService")
	private IUserService userService;

	private static final Logger logger = LogManager
			.getLogger(PasswordController.class);

	/****
	 * 进入系统发送重置密码邮件页
	 * 
	 * @param parent_id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public ModelAndView forgotpassword(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("front/reset_password/forgotpassword");
	}

	/****
	 * 发送重置密码邮件
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sendresetpasswordemail", method = RequestMethod.POST)
	@ResponseBody
	public Object sendresetpasswordemail(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("email");

		if (StringUtil.isEmpty(email)) {
			return this.handleValidateFalse("请输入有效的邮箱");
		}

		RequestResult rr = new RequestResult();

		try {

			User user = this.userService.getUserInfByUserEmail(email);
			if (user == null) {
				return this.handleValidateFalse("用户邮箱未注册,请区分邮箱名的大小写");
			}

			String resetpwdcode = EncryptMou.encrypt(email);

			// 1.设置用户的重置密码的重置码
			userService.updateresetpwdcode(email, resetpwdcode);

			// 2.发送重置密码邮件
			Map<String, String> params = new HashMap<String, String>();
			params.put("email", email);
			params.put("nickname", email);
			params.put("date", DateUtil.getCurdate());
			params.put("resetpwdcode", resetpwdcode);
			userMailService.sendResetPasswordEmail(params, email);

			rr.setSuccess(true);

			return rr;
		} catch (Exception e) {

			return this.handleException(e);
		}
	}

	/****
	 * 进入系统发送重置密码邮件页
	 * 
	 * @param parent_id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toresetpassword", method = RequestMethod.GET)
	public ModelAndView toresetpassword(Model model,
			HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("email");
		String resetpwdcode = request.getParameter("resetpwdcode");

		String validresult = userService.validateResetPassword(email,
				resetpwdcode);

		model.addAttribute("email", email);
		model.addAttribute("validresult", validresult);
		model.addAttribute("resetpwdcode", resetpwdcode);
		return new ModelAndView("front/reset_password/resetpassword");
	}

	/****
	 * 重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public Object resetpassword(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("重置密码");

		RequestResult rr = new RequestResult();

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String resetpwdcode = request.getParameter("resetpwdcode");

		if (StringUtil.isEmpty(email) || StringUtil.isEmpty(password)
				|| StringUtil.isEmpty(resetpwdcode)) {

			return this.handleValidateFalse("参数非法");
		}

		try {

			// 1. 判断邮箱是否注册，如果未注册，返回
			User user = this.userService.getUserInfByUserEmail(email);
			if (user == null) {
				return this.handleValidateFalse("用户邮箱未注册,请区分邮箱名的大小写");
			}

			userService.updateresetpwdcode(email.trim(), password.trim(),
					resetpwdcode.trim());

			// 2.发送修改密码成功邮件
			Map<String, String> params = new HashMap<String, String>();
			params.put("email", email);
			params.put("date", DateUtil.getCurdate());
			params.put("nickname", email);

			params.put("optime", DateUtil.getCurrentTimsmpS());
			params.put("opsystem", this.getUserOS());
			params.put("opbrowser", this.getUserBrowser());
			params.put("opip", this.getUserIP());

			userMailService.sendResetPasswordSuccessEmail(params, email);

			rr.setSuccess(true);

			return rr;
		} catch (Exception e) {

			return this.handleException(e);
		}
	}

}
