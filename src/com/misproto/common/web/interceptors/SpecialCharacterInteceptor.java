package com.misproto.common.web.interceptors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/****
 * ThreadContext拦截器<br>
 * 对每一个请求，设置和清理线程级的缓存<br>
 * 如： 查询的分页参数等等
 * 
 * @author sks
 *
 */
public class SpecialCharacterInteceptor implements HandlerInterceptor {

	private static final Logger logger = LogManager
			.getLogger(SpecialCharacterInteceptor.class);

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

		String param = "";
		String paramValue = "";
		Enumeration params = request.getParameterNames();
		// 循环读取参数
		while (params.hasMoreElements()) {

			param = (String) params.nextElement(); // 获取请求中的参数
			String[] values = request.getParameterValues(param);// 获得每个参数对应的值

			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {

					paramValue = values[i];

					// 转换目标字符变成对象字符，可以多个。后期扩展特殊字符库用于管理
					// paramValue = paramValue.replaceAll("'", "");
					paramValue = paramValue.replaceAll("\"", "&quot;");

					// 这里还可以增加，如领导人 自动转义成****,可以从数据库中读取非法关键字。
					values[i] = paramValue;
				}
			}
		}
		return true;
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}
}
