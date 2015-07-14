package com.misproto.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;

import com.misproto.common.util.useragent.UserAgent;
import com.misproto.common.util.useragent.UserAgentUtil;

public class HttpServletRequestUtil {

	private static final Logger logger = LogManager
			.getLogger(HttpServletRequestUtil.class);

	/****
	 * 获取远程客户端的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRemoteInf(HttpServletRequest request) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String agent = request.getHeader("user-agent");
		logger.info("useragent == null!");
		logger.info("agent\n{}",agent);
		UserAgent useragent = UserAgentUtil.getUserAgent(agent);
		
		if (useragent == null){
			logger.info("useragent == null!");
			logger.info("agent\n{}",agent);
		}

		if (useragent != null && StringUtil.isNotEmpty(useragent.getBrowserType())) {
			resultMap.put("userbrowser", useragent.getBrowserType());
			resultMap.put("userbrowser_version", useragent.getBrowserVersion());
			resultMap.put("useros", useragent.getPlate_type_series());
			resultMap.put("useragent", agent);
		} else {
			resultMap.put("userbrowser", "");
		}
	
		resultMap.put("userip", getClientIp(request));

		return resultMap;
	}

	/****
	 * 在很多应用下都可能有需要将用户的真实IP记录下来，这时就要获得用户的真实IP地址<br>
	 * 通常，获取客户端的IP地 址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。<br>
	 * 但是在通过了Apache,Squid等 反向代理软件就不能获取到客户端的真实IP地址了。
	 * 但是在转发请求的HTTP头信息中，增加了X－FORWARDED－FOR信息。<br>
	 * 用以跟踪原有的客户端IP地址和原来客户端请求的服务器地址。
	 * 
	 * @param request
	 * @return
	 */
	private static String getClientIp(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {

			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		if (StringUtil.isNotEmpty(ipAddress)) {
			ipAddress = ipAddress.split(",")[0];
		}
		return ipAddress;
	}

	public static void debugParams(HttpServletRequest request) {

		logger.debug("-----------<params>----------");

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {

			String key = (String) paramNames.nextElement();

			String[] values = request.getParameterValues(key);

			if (values.length > 1) {
				logger.debug(key + "-->" + JsonUtil.toJsonStr(values));
			} else {
				logger.debug(key + "-->[" + values[0] + "]");
			}
		}

		logger.debug("----------</params>----------");
	}

	public static String getParams(HttpServletRequest request) {

		StringBuilder sb = new StringBuilder();

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {

			String key = (String) paramNames.nextElement();

			String[] values = request.getParameterValues(key);

			if (values.length > 1) {
				sb.append(key + "-->" + JsonUtil.toJsonStr(values)).append("&");
			} else {
				sb.append(key + "-->[" + values[0] + "]").append("&");
			}
		}

		return sb.toString();
	}

	/****
	 * 清除所有session缓存
	 * 
	 * @param request
	 */
	public static void clearAllSessionAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session == null) {
			return;
		}

		Enumeration<String> attributes = session.getAttributeNames();

		while (attributes.hasMoreElements()) {

			String key = (String) attributes.nextElement();
			session.removeAttribute(key);
		}
	}

	/****
	 * 取所有的session缓存属性
	 * 
	 * @param request
	 * @return
	 */
	public static String getAllSessionAttributes(HttpServletRequest request) {

		HttpSession session = request.getSession();

		if (session == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Enumeration<String> attributes = session.getAttributeNames();

		while (attributes.hasMoreElements()) {

			String key = (String) attributes.nextElement();
			Object value = session.getAttribute(key);

			sb.append(key).append("-->[").append(JsonUtil.toJsonStr(value))
					.append("]\n");
		}

		return sb.toString();
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
	public static String[] getUriController(HttpServletRequest request) {
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
