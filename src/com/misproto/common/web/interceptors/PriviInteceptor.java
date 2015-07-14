package com.misproto.common.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.JsonUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.misproto.Constant;
import com.misproto.common.util.HttpServletRequestUtil;
import com.misproto.modules.global.model.SessionPublicData;
import com.misproto.modules.user.model.User;

/****
 * 权限拦截器
 * 
 * @author sks
 *
 */
public class PriviInteceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager
			.getLogger(PriviInteceptor.class);

	private static final String LOGKEY = "ACCESSTIMELOG";

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		if (request.getAttribute(PriviInteceptor.LOGKEY) != null) {
			StringBuilder sb = new StringBuilder();
			sb.append((String) (request.getAttribute(PriviInteceptor.LOGKEY)));
			sb.append("<endtime>" + DateUtil.getCurrentTimsmp() + "</endtime>");
			sb.append("</controller>");

			logger.info(sb.toString());
		}
	}

	// 控制器执行完，生成视图之前可以做的动作，比如，加入公共成员（日期）
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	/****
	 * 可以确定此handler为下一步要执行的拦截器或者Controller。<br>
	 * 我们都知道拦截器可以配置多个，就有个拦截器链，按照顺序去执行这么多拦截器<br>
	 * 如果你正在执行的拦截器完成后，下面还有个拦截器等待执行，那么handler就是那个拦截器类；<br>
	 * 如果这个拦截器执行完了 ，就执行controller，那么这个handler就是那个Controller类了。
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String[] uri_controller = getUriController(request);

		String controller = uri_controller[1];
		String controller_head = uri_controller[2];

		if (controller.indexOf(".") < 0) {
			// 访问为controller时，才记录
			// 资源文件的访问不记录

			StringBuilder sb = new StringBuilder();
			sb.append("<controller>");
			sb.append("<name>" + controller + "</name>");
			sb.append("<url>" + controller + "</url>");
			sb.append("<params>" + HttpServletRequestUtil.getParams(request)
					+ "</params>");
			sb.append("<starttime>" + DateUtil.getCurrentTimsmp()
					+ "</starttime>");
			request.setAttribute(PriviInteceptor.LOGKEY, sb.toString());
		}

		HttpServletRequestUtil.debugParams(request);

		if (controller_head.indexOf("admin") >= 0) {

			logger.debug(JsonUtil.toJsonStr(uri_controller));
			logger.debug("controller_head[" + controller_head + "]");

			// 如果是登陆首页，直接返回true
			if (controller_head.startsWith("adminlog")) {
				return true;
			}

			if (controller_head.toLowerCase().endsWith("admin")) {

				SessionPublicData sPublicData = (SessionPublicData) request
						.getSession().getAttribute(
								Constant.SESSION_CASH_PUBLICDATA);

				// 校验 如果没有登陆，则转向登陆页面
				ModelAndView mav = getMVtoGo(controller_head, sPublicData);
				if (mav != null) {
					throw new ModelAndViewDefiningException(mav);
				}
			}
		}

		return true;

		// 3. 判断动作是否授权

		// String priActions = (String)
		// request.getSession().getAttribute("PRIVACTIONS");
		//
		// if (priActions.indexOf(action) < 0) {
		//
		// // 没有授权，则返回授权失败页面
		// ModelAndView mav = new ModelAndView("system/authorizeError");
		// throw new ModelAndViewDefiningException(mav);
		// }

		// return true;
	}

	/***
	 * 根据用户身份和controller判断是否需要拦截，需要的话，返回相应的ModelAndView，不需要的话，返回null
	 * 
	 * @param controller_head
	 * @param user
	 * @return
	 */
	private ModelAndView getMVtoGo(String controller_head,
			SessionPublicData sPublicData) {

		ModelAndView mav = null;
		if (sPublicData == null) {
			if (controller_head.toLowerCase().equals("specificationitemadmin")) {
				//mav = new ModelAndView("front/login/login");
				return null;
			} else {
				mav = new ModelAndView("admin/login/adminlogin");
			}
		} else {
			User user = sPublicData.getLoginUser();
			// 如果已登录，但不是系统人员，则转回登录首页
			if (user.getCharacter() == null
					|| !user.getCharacter().equals(Constant.USER_CHARACTER_SYS)) {

				mav = new ModelAndView("admin/login/adminlogin");
			}
		}

		return mav;
	}

	/****
	 * 获取uri和controller<br>
	 * [uri,controller,controllerhead]<br>
	 * 样例：<br>
	 * ["/WEBNIU2/dpt/toDetail.do?123213210i\u003d1fioafewi?jfoeajo",
	 * "dpt/toDetail.do","dpt"]
	 * 
	 * @param request
	 * @return
	 */
	private String[] getUriController(HttpServletRequest request) {
		String[] rtnArray = new String[3];

		String uri = request.getRequestURI();
		String ctx = request.getContextPath();

		String controller = null;
		int idx_start = ctx.length() + 1;
		int idx_end = uri.indexOf("?");

		if (idx_end > 0) {
			controller = uri.substring(idx_start, idx_end);
		} else {
			controller = uri.substring(idx_start);
		}

		String action_head = controller;
		int idx_head = controller.indexOf("/");

		if (idx_head > 0) {
			action_head = controller.substring(0, idx_head);
		}

		rtnArray[0] = uri;
		rtnArray[1] = controller;
		rtnArray[2] = action_head;
		return rtnArray;
	}

	public static void main(String[] args) {
		String uri = "/WEBNIU2/dpt/toDetail.do?123213210i=1fioafewi?jfoeajo";
		// String uri = "/WEBNIU2/dpt/toDetail.do";

		uri = "/WEBNIU2/admin";

		String ctx = "/WEBNIU2";

		int idx_start = ctx.length() + 1;
		String action = null;

		int idx_end = uri.indexOf("?");

		if (idx_end > 0) {
			action = uri.substring(idx_start, idx_end);
		} else {
			action = uri.substring(idx_start);
		}

		String action_head = action;
		int idx_head = action.indexOf("/");

		if (idx_head > 0) {
			action_head = action.substring(0, idx_head);
		}

		String[] rtnArray = new String[3];
		rtnArray[0] = uri;
		rtnArray[1] = action;
		rtnArray[2] = action_head;

		System.out.println("\n----------------------------------\n" + uri);
		System.out.println(JsonUtil.toJsonStr(rtnArray));

		// System.out.println("\n----------------uri---------------\n" + uri);
		// System.out.println("\n----------------ctx---------------\n" + ctx);
		// System.out
		// .println("\n----------------action---------------\n" + action);
	}
}
