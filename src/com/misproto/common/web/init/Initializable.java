package com.misproto.common.web.init;

import javax.servlet.ServletContext;

/****
 * 系统的初始化接口
 * 
 * @author NBQ
 *
 */
public interface Initializable {
	public void init(ServletContext servletContext);
}
