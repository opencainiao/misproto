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
import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;
import org.mou.common.security.EncryptMou;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.misproto.Constant;
import com.misproto.common.globalhandler.ErrorHandler;
import com.misproto.common.globalobj.RequestResult;
import com.misproto.modules.base.BaseController;
import com.misproto.modules.mail.IUserMailService;
import com.misproto.modules.user.enumes.RegistResult;
import com.misproto.modules.user.enumes.UserActiveResult;
import com.misproto.modules.user.model.User;
import com.misproto.modules.user.service.IUserRegisterService;
import com.misproto.modules.user.service.IUserService;

/****
 * 注册控制器
 * 
 * @author NBQ
 */
@Controller
public class UserRegisterController extends BaseController {

	@Resource
	public IUserMailService userMailService = null;

	@Resource(name = "userRegisterService")
	private IUserRegisterService userRegisterService;

	@Resource(name = "userService")
	private IUserService userService;

	private static final Logger logger = LogManager
			.getLogger(UserRegisterController.class);

	/****
	 * 进入用户注册系统页面（前端用户注册）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/usersignup", method = RequestMethod.GET)
	public ModelAndView tousersignup(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("front/login/usersignup");
	}

	/****
	 * 用户注册（前端用户注册）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/usersignup", method = RequestMethod.POST)
	@ResponseBody
	public Object usersignup(@Validated User registeruser, BindingResult br,
			HttpServletRequest request) {

		logger.debug("-------------usersignup-------------");

		if (br.hasErrors()) {
			logger.debug(JsonUtil.toJsonStr(br.getFieldErrors()));
			return ErrorHandler.getRequestResultFromBindingResult(br);
		}

		logger.debug("传入的注册信息");
		logger.debug(registeruser);

		String validateResult = registeruser.valideNoEmptyForEmailRegister();
		if (!validateResult.equals("")) {
			return this.handleValidateFalse(validateResult);
		}

		RequestResult rr = new RequestResult();

		try {
			String result = this.userRegisterService
					.registWithEmail(registeruser);

			RegistResult resultObj = null;

			if (result.equals(RegistResult.EXIST.getCode())) {
				rr.setSuccess(false);
				resultObj = RegistResult.EXIST;
			} else {

				String activecode = result.split("_")[1];
				logger.debug("activecode[{}]", activecode);

				if (Constant.USER_REGIST_NEED_ACTIVE_FLG.equals("1")) {
					// 发送激活邮件

					String receiver = registeruser.getEmail();

					Map<String, String> params = new HashMap<String, String>();
					params.put("email", receiver);
					params.put("date", DateUtil.getCurdate());
					params.put("activecode", activecode);

					this.userMailService.sendActiveEmail(params, receiver);
				}

				rr.setSuccess(true);
				resultObj = RegistResult.SUCCESS;
			}

			rr.setMessage(resultObj.getCode());
			return rr;
		} catch (Exception e) {

			return this.handleException(e);
		}
	}

	/****
	 * 进入发送激活邮箱页面（前端用户激活）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tosendactiveemail", method = RequestMethod.GET)
	public ModelAndView tosendactiveemail(Model model,
			HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("front/login/tosendactiveemail");
	}

	/****
	 * 发送激活邮件（前端用户激活）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/senduseractiveemail", method = RequestMethod.POST)
	@ResponseBody
	public Object senduseractiveemail(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String email = request.getParameter("email");

		if (StringUtil.isEmpty(email)) {
			return this.handleValidateFalse("请输入有效的邮箱");
		}

		RequestResult rr = new RequestResult();

		try {

			Map<String, String> params = new HashMap<String, String>();
			String activecode = EncryptMou.encrypt(email);
			params.put("email", email);
			params.put("date", DateUtil.getCurdate());
			params.put("activecode", activecode);

			// 1. 判断邮箱是否注册，如果未注册，返回
			User user = this.userService.getUserInfByUserEmail(email);
			if (user == null) {
				return this.handleValidateFalse("用户邮箱未注册,请区分邮箱名的大小写");
			}

			// 2.如果已激活，提示用户已激活
			if (user.isActive()) {
				return this.handleValidateFalse("用户已激活");
			}

			// 3.更新激活码
			userService.updateactivecode(email, activecode);

			// 4.发送激活邮件
			userMailService.sendActiveEmail(params, email);

			rr.setSuccess(true);
			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}

	}

	/****
	 * 用户激活（前端用户激活）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/useractive", method = RequestMethod.GET)
	public ModelAndView useractive(Model model, HttpServletRequest request) {

		logger.debug("-------------useractive-------------");
		try {
			String email = request.getParameter("email");
			String activecode = request.getParameter("activecode");

			if (StringUtil.isEmpty(email) || StringUtil.isEmpty(activecode)) {
				model.addAttribute("resultcode",
						UserActiveResult.TIMEOUT.getCode());
			} else {
				String result = this.userRegisterService.active(email.trim(),
						activecode.trim());

				UserActiveResult resultObj = null;
				if (result.equals(UserActiveResult.ACTIVECODEERROR.getCode())) {
					resultObj = UserActiveResult.ACTIVECODEERROR;
				} else if (result.equals(UserActiveResult.TIMEOUT.getCode())) {
					resultObj = UserActiveResult.TIMEOUT;
				} else {
					resultObj = UserActiveResult.SUCCESS;
				}

				model.addAttribute("resultcode", resultObj.getCode());
			}
		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("resultcode",
					UserActiveResult.ACTIVECODEERROR.getCode());
		}

		return new ModelAndView("front/login/user_active_result");
	}

}
