package com.misproto.common.web.interceptors;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.misproto.Constant;
import com.misproto.common.cash.context.Contexkeys;
import com.misproto.common.cash.context.ThreadContextManager;
import com.misproto.common.globalhandler.PageSearchResultHandler;
import com.misproto.common.util.HttpServletRequestUtil;
import com.misproto.modules.global.model.SessionPublicData;

/****
 * ThreadContext拦截器<br>
 * 对每一个请求，设置和清理线程级的缓存<br>
 * 如： 查询的分页参数等等
 * 
 * @author sks
 *
 */
public class ContextInteceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager
			.getLogger(ContextInteceptor.class);

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

		if (controller.indexOf(".") < 0) {
			// 访问为controller时，才设置
			// 资源文件的访问不设置
			// 0.设置客户端信息
			Map<String, String> remoteInf = HttpServletRequestUtil
					.getRemoteInf(request);
			ThreadContextManager.put(Contexkeys.USERBROWSER,
					remoteInf.get("userbrowser"));
			ThreadContextManager.put(Contexkeys.USERAGENT,
					remoteInf.get("useragent"));
			ThreadContextManager.put(Contexkeys.USERBROWSER_VERSION,
					remoteInf.get("userbrowser_version"));
			ThreadContextManager
					.put(Contexkeys.USEROS, remoteInf.get("useros"));
			ThreadContextManager
					.put(Contexkeys.USERIP, remoteInf.get("userip"));

			// 1.将session中的信息放入当前线程中
			SessionPublicData sPublicData = (SessionPublicData) request
					.getSession()
					.getAttribute(Constant.SESSION_CASH_PUBLICDATA);

			if (sPublicData != null) {
				ThreadContextManager.put(Contexkeys.USER,
						sPublicData.getLoginUser());
				ThreadContextManager.put(Contexkeys.USERID, sPublicData
						.getLoginUser().get_id().toString());
				ThreadContextManager.put(Contexkeys.USERNAME, sPublicData
						.getLoginUser().getUsername());
			}

			// 2.设置分页查询条件
			Map<String, Integer> pageInfo = PageSearchResultHandler
					.getPageInfoFromRequest(request);
			ThreadContextManager.put(Contexkeys.PAGEINFO_SEARCH, pageInfo);

			// 打印缓存信息
			StringBuilder sb = new StringBuilder();
			sb.append("\n线程级缓存如下：\n" + sb.toString());
			sb.append("<thread_context>");
			sb.append(ThreadContextManager.getAllCashedInf());
			sb.append("</thread_context>");
			logger.debug(sb.toString());
		}

		return true;
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		if (!ThreadContextManager.isEmpty()) {

			StringBuilder sb = new StringBuilder();
			sb.append("\n清除前，线程级缓存如下：\n" + sb.toString());
			sb.append("<thread_context>");
			sb.append(ThreadContextManager.getAllCashedInf());
			sb.append("</thread_context>");

			ThreadContextManager.clear();

			sb.append("\n清除后，线程级缓存如下：\n");
			sb.append("<thread_context>");
			sb.append(ThreadContextManager.getAllCashedInf());
			sb.append("</thread_context>");

			logger.debug(sb.toString());
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
}
