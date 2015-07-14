package com.misproto.common.web.init.initors;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import com.misproto.common.system.DBManager;
import com.misproto.common.web.init.Initializable;

/****
 * 初始化系统环境变量数据（如：系统名称、全局上下文路径）
 * 
 * @author NBQ
 *
 */
@Component("sysInfoInitor")
public class SysInfoInitor implements Initializable {

	/****
	 * 初始化系统内存数据，避免硬编码
	 */
	public void init(ServletContext servletContext) {

		// 系统名称
		String sysnam = servletContext.getInitParameter("sysnam");
		servletContext.setAttribute("sysnam", sysnam);

		// 全局上下文路径
		servletContext.setAttribute("ctx", servletContext.getContextPath());

		 DBManager.initDB("bxb");

//		MongoClientManager.init("182.92.238.54", "bxb");
	}
}
