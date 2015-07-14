package com.misproto.modules.user.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;
import org.mou.common.security.EncryptMou;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.misproto.Constant;
import com.misproto.common.globalobj.RequestResult;
import com.misproto.common.util.HttpServletRequestUtil;
import com.misproto.modules.global.model.SessionPublicData;
import com.misproto.modules.user.enumes.LoginAction;
import com.misproto.modules.user.enumes.LoginState;
import com.misproto.modules.user.model.LoginInf;
import com.misproto.modules.user.model.LoginLogInf;
import com.misproto.modules.user.model.LoginUserInf;
import com.misproto.modules.user.model.User;
import com.misproto.modules.user.service.ILoginInfService;
import com.misproto.modules.user.service.IUserService;

/****
 * 登录控制器
 * 
 * @author NBQ
 * @date 2012-3-27
 */
@Controller
public class UserLoginController {

	private static final Logger logger = LogManager
			.getLogger(UserLoginController.class);

	@Resource(name = "userService")
	private IUserService userService;
	
	@Resource(name = "loginInfService")
	private ILoginInfService loginInfService;

	/****
	 * 用户登录系统页面（前端用户登陆系统）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.GET)
	public ModelAndView touserlogin(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String from = request.getParameter("from");

		logger.debug("进入登陆页面，参数from[{}]", from);

		model.addAttribute("from", from);

		return new ModelAndView("front/login/login");
	}

	/****
	 * 用户登录系统（前端用户登陆系统）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public Object userlogin(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		RequestResult rr = new RequestResult();

		String usernameoremail = request.getParameter("ure");
		String password = request.getParameter("password");

		if (StringUtil.isEmpty(usernameoremail) || StringUtil.isEmpty(password)) {

			rr.setSuccess(false);
			rr.setMessage("请输入用户名和密码");

			return rr;
		}

		try {
			// 1. 查询账号信息
			User user = userService.getUserInfByUserNameOrEmail(usernameoremail
					.trim());
			if (user == null) {
				rr.setSuccess(false);
				rr.setMessage("用户不存在");
				return rr;
			}

			// 2.校验用户密码
			String passwordHashed = user.getPassword();
			if (!EncryptMou.validate(password, passwordHashed)) {
				rr.setSuccess(false);
				rr.setMessage("用户密码不正确");
				return rr;
			}

			// 3.校验用户状态
			if (!user.isValidUser()) {
				rr.setSuccess(false);
				rr.setMessage("用户状态不合法，请联系系统管理员");
				return rr;
			}

			// 4.设置登陆信息
			String logInTime = DateUtil.getCurrentTimsmp();
			LoginInf loginInf = new LoginInf();
			loginInf.setLogoutTime("");
			loginInf.setLoginTime(logInTime);
			loginInf.setLoginIp(request.getRemoteAddr());
			loginInf.setLastOpTime(logInTime);
			loginInf.setLoginState(LoginState.IN.getCode());
			loginInf.setLoginStateName(LoginState.IN.getName());

			// 5.设置缓存对象
			LoginUserInf loginuserinf = new LoginUserInf();
			loginuserinf.setUser(user);
			loginuserinf.setLoginInf(loginInf);

			SessionPublicData sPublicData = new SessionPublicData(); // session级缓存
			sPublicData.setLoginUserInf(loginuserinf);

			// 6. 将缓存对象放入session
			HttpSession session = request.getSession(true);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("nickname", user.getNick());
			session.setAttribute(Constant.SESSION_CASH_PUBLICDATA, sPublicData);

			logger.debug(session.getAttribute(Constant.SESSION_CASH_PUBLICDATA));

			// 7.设置全局登陆信息
			loginInfService.saveLoginInf(loginuserinf);

			// 8.登记登陆日志
			LoginLogInf logInf = new LoginLogInf();
			logInf.setActioncode(LoginAction.IN.getCode());
			logInf.setActionName(LoginAction.IN.getName());
			logInf.setOpTime(logInTime);
			logInf.setUsername(user.getUsername());
			logInf.setIp(request.getRemoteAddr());
			loginInfService.saveLoginlog(logInf);

			rr.setSuccess(true);
			rr.setMessage(JsonUtil.toJsonStr(user.toSummary()));
			return rr;

		} catch (Exception e) {
			e.printStackTrace();

			rr.setSuccess(false);
			rr.setMessage(StringUtil.getStackTrace(e));

			return rr;
		}
	}

	/****
	 * 系统退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/userlogout", method = RequestMethod.GET)
	public ModelAndView userlogout(HttpServletRequest request,
			HttpServletResponse response) {

		String username = (String) request.getSession()
				.getAttribute("username");

		logger.debug("用户退出logout[" + username + "]");

		if (StringUtil.isNotEmpty(username)) {
			try {
				SessionPublicData sPublicData = (SessionPublicData) request
						.getSession().getAttribute(
								Constant.SESSION_CASH_PUBLICDATA);
				if (sPublicData != null) {
					LoginUserInf loginuserinf = sPublicData.getLoginUserInf();

					String logOutTime = DateUtil.getCurrentTimsmp();

					if (loginuserinf != null) {

						// 设置登陆状态为退出
						LoginInf loginInf = new LoginInf();
						loginInf.setLogoutTime(logOutTime);
						loginInf.setLoginTime("");
						loginInf.setLoginIp("");
						loginInf.setLastOpTime(logOutTime);
						loginInf.setLoginState(LoginState.NOTIN.getCode());
						loginInf.setLoginStateName(LoginState.NOTIN.getName());

						loginuserinf.setLoginInf(loginInf);

						loginInfService.saveLoginInf(loginuserinf);
					}

					// 登记日志
					LoginLogInf logInf = new LoginLogInf();
					logInf.setActioncode(LoginAction.OUT.getCode());
					logInf.setActionName(LoginAction.OUT.getName());
					logInf.setOpTime(logOutTime);
					logInf.setUsername(username);
					logInf.setIp(request.getRemoteAddr());

					loginInfService.saveLoginlog(logInf);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("<logout>");
				logger.error(StringUtil.getStackTrace(e));
				logger.error("</logout>");
			}
		}

		logger.debug("清除缓存前的缓存属性如下");
		logger.debug(HttpServletRequestUtil.getAllSessionAttributes(request));
		logger.debug("-----");

		// 清除session缓存
		HttpServletRequestUtil.clearAllSessionAttributes(request);

		logger.debug("清除缓存后的缓存属性如下");
		logger.debug(HttpServletRequestUtil.getAllSessionAttributes(request));
		logger.debug("-----");

		// 失效缓存
		request.getSession().invalidate();

		logger.debug("缓存失效后的缓存属性如下");
		logger.debug(HttpServletRequestUtil.getAllSessionAttributes(request));
		logger.debug("-----");

		return new ModelAndView("front/login/logout");
	}

	/****
	 * 用户登录系统成功后的首页（前端用户登陆系统）
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userhome", method = RequestMethod.GET)
	public ModelAndView userhome(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String from = request.getParameter("from");

		model.addAttribute("from", from);

		return new ModelAndView("front/home/index");
	}
}
