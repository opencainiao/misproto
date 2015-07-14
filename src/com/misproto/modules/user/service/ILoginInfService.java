package com.misproto.modules.user.service;

import com.misproto.modules.user.model.LoginLogInf;
import com.misproto.modules.user.model.LoginUserInf;

/****
 * 系统级缓存服务<br>
 * 存储所有用户的登陆信息
 * 
 * @author NBQ
 *
 */
public interface ILoginInfService {

	/****
	 * 根据用户名查找登陆信息
	 * 
	 * @param loginUserInf
	 */
	public LoginUserInf getLoginInf(String username);

	/****
	 * 登记用户登陆信息至全局登陆信息表
	 * 
	 * @param loginUserInf
	 */
	public void saveLoginInf(LoginUserInf loginUserInf);

	/****
	 * 登记用户登陆日志信息至全局登陆信息表
	 * 
	 * @param loginUserInf
	 */
	public void saveLoginlog(LoginLogInf loginlog);

}
