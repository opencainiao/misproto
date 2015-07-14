package com.misproto.modules.global.model;

import com.misproto.modules.user.model.LoginInf;
import com.misproto.modules.user.model.LoginUserInf;
import com.misproto.modules.user.model.User;

/****
 * session级缓存,存储在HttpSession中
 * 
 * @author NBQ
 *
 */
public class SessionPublicData {

	public LoginUserInf loginUserInf;

	/****
	 * 取登陆用户名
	 * 
	 * @return
	 */
	public String getLoginUserName() {

		return this.loginUserInf.getUser().getUsername();
	}

	/****
	 * 取登陆用户昵称
	 * 
	 * @return
	 */
	public String getLoginUserNickName() {
		return this.loginUserInf.getUser().getNick();
	}

	/****
	 * 取登陆用户邮箱
	 * 
	 * @return
	 */
	public String getLoginUserEmail() {
		return this.loginUserInf.getUser().getEmail();
	}

	/****
	 * 取登陆的用户信息
	 * 
	 * @return
	 */
	public User getLoginUser() {
		return this.loginUserInf.getUser();
	}

	/****
	 * 取用户的登陆信息
	 * 
	 * @return
	 */
	public LoginInf getLoginInf() {
		return this.loginUserInf.getLoginInf();
	}

	public LoginUserInf getLoginUserInf() {
		return loginUserInf;
	}

	public void setLoginUserInf(LoginUserInf loginUserInf) {
		this.loginUserInf = loginUserInf;
	}

}
