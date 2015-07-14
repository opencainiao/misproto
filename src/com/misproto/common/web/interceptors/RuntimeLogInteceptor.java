package com.misproto.common.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.JsonUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.misproto.common.cash.context.Contexkeys;
import com.misproto.common.cash.context.ThreadContextManager;
import com.misproto.common.util.HttpServletRequestUtil;

/****
 * 执行时间拦截器
 * 
 * @author sks
 *
 */
public class RuntimeLogInteceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager
			.getLogger(RuntimeLogInteceptor.class);

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

		String nowTime = DateUtil.getCurrentTimsmp();

		if (controller.indexOf(".") < 0) {
			// 访问为controller时，才记录
			// 资源文件的访问不记录

			StringBuilder sb = new StringBuilder();
			sb.append("<controller>");
			sb.append("<name>" + controller + "</name>");
			sb.append("<url>" + uri_controller[0] + "</url>");
			sb.append("<params>" + HttpServletRequestUtil.getParams(request)
					+ "</params>");
			sb.append("<starttime>" + nowTime + "</starttime>");

			ThreadContextManager.put(Contexkeys.REQUEST_STARTTIME, nowTime);
			ThreadContextManager.put(Contexkeys.REQUEST_RUNTIMELOG,
					sb.toString());
		}

		return true;
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		String nowTime = DateUtil.getCurrentTimsmp();
		if (ThreadContextManager.containsKey(Contexkeys.REQUEST_STARTTIME)) {
			StringBuilder sb = new StringBuilder();
			sb.append((String) (ThreadContextManager
					.get(Contexkeys.REQUEST_RUNTIMELOG)));
			sb.append("<endtime>" + nowTime + "</endtime>");
			
			int timeBetween_milis = DateUtil.getIntervalTimeStampMillis(
					nowTime, (String) (ThreadContextManager
							.get(Contexkeys.REQUEST_STARTTIME)));
			int timeBetween_seconds = DateUtil.getIntervalTimeStampSecond(
					nowTime, (String) (ThreadContextManager
							.get(Contexkeys.REQUEST_STARTTIME)));
			int timeBetween_minuts = DateUtil.getIntervalTimeStampMinute(
					nowTime, (String) (ThreadContextManager
							.get(Contexkeys.REQUEST_STARTTIME)));

			sb.append("<runtime>" + timeBetween_minuts + "_"
					+ timeBetween_seconds + "_" + timeBetween_milis
					+ "</runtime>");
			sb.append("</controller>");

			logger.info(sb.toString());
		}
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
