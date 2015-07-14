package com.misproto.common.web.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.StringUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import com.misproto.common.util.HttpServletRequestUtil;

public class SpecialCharacterFilter extends OncePerRequestFilter {

	private static Set<String> notFiltered;
	private final Logger logger = LogManager
			.getLogger(SpecialCharacterFilter.class);

	static {
		notFiltered = new HashSet<String>();
		notFiltered.add("materialhome/add");
		notFiltered.add("materialtypehome/add_detail");
		notFiltered.add("materialtypehome/update_detail");
		notFiltered.add("materialtypehome/add_supplier");
		notFiltered.add("materialtypehome/update_supplier");

	}

	private boolean noFilter(String controller) {

		String[] app = controller.split("/");

		if (app.length == 3) {
			controller = app[0] + "/" + app[2];
		}
		logger.debug("to_check_controller[" + controller + "]");

		return notFiltered.contains(controller);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String[] uri_controller = HttpServletRequestUtil
				.getUriController(request);

		// 不过滤，很多特殊传参场景，无法统一过滤处理
		// String controller_head = uri_controller[2].toLowerCase();
		String controller_head2 = uri_controller[1].toLowerCase();

		if (controller_head2.indexOf("add") >= 0
				|| controller_head2.indexOf("update") >= 0
				|| controller_head2.indexOf("save") >= 0) {

			String method = request.getMethod().toUpperCase();

			boolean noFilter = noFilter(controller_head2);

			if (method.equals("POST") && !noFilter) {
				// 写入数据库的操作，才需要过滤
				request = new Request((HttpServletRequest) request);
			}
		}

		chain.doFilter(request, response);
	}

	/****
	 * 过滤特殊字符
	 * 
	 * @param value
	 * @return
	 */
	public String filterDangerString(String value) {
		if (value == null) {
			return null;
		}

		value = StringEscapeUtils.escapeHtml4(value);
		value = StringUtil.escapeHtmlToCode(value);

		return value;
	}

	class Request extends HttpServletRequestWrapper {
		public Request(HttpServletRequest request) {
			super(request);
		}

		@Override
		public String getParameter(String name) {
			// 返回值之前 先进行过滤
			return filterDangerString(super.getParameter(name));
		}

		@Override
		public String[] getParameterValues(String name) {
			// 返回值之前 先进行过滤
			String[] values = super.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				values[i] = filterDangerString(values[i]);
			}
			return values;
		}
	}

}
