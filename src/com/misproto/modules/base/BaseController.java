package com.misproto.modules.base;

import java.util.List;

import org.mou.common.StringUtil;

import com.misproto.common.cash.context.Contexkeys;
import com.misproto.common.cash.context.ThreadContextManager;
import com.misproto.common.globalhandler.PageSearchResultHandler;
import com.misproto.common.globalobj.PageVO;
import com.misproto.common.globalobj.RequestResult;
import com.misproto.common.util.ValidateUtil;
import com.misproto.common.util.useragent.UserAgentUtil;
import com.misproto.modules.user.model.User;
import com.mongodb.DBObject;

public class BaseController {

	/****
	 * 判断是否合法的objectId
	 * 
	 * @param _id
	 * @return
	 */
	public boolean isValidObjId(String _id) {
		return ValidateUtil.isValidObjId(_id);
	}

	public RequestResult handleException(Exception e) {

		e.printStackTrace();
		RequestResult rr = new RequestResult();
		rr.setSuccess(false);
		rr.setMessage(StringUtil.getStackTrace(e));

		return rr;
	}

	/****
	 * 对校验不合法的情况进行处理，生成对应的返回对象
	 * 
	 * @param message
	 * @return
	 */
	public RequestResult handleValidateFalse(String message) {

		RequestResult rr = new RequestResult();
		rr.setSuccess(false);
		rr.setMessage(message);

		return rr;
	}

	public boolean isLowBrowser() {
		return UserAgentUtil.isLowBrowser(getUserBrowser(),
				getUserBrowserVersion());
	}

	/****
	 * 取客户端的浏览器
	 * 
	 * @return
	 */
	public String getUserBrowser() {
		return (String) ThreadContextManager.get(Contexkeys.USERBROWSER);
	}

	/****
	 * 取客户端的浏览器agent
	 * 
	 * @return
	 */
	public String getUseragent() {
		return (String) ThreadContextManager.get(Contexkeys.USERAGENT);
	}

	/****
	 * 取客户端的浏览器浏览器版本
	 * 
	 * @return
	 */
	public String getUserBrowserVersion() {
		return (String) ThreadContextManager
				.get(Contexkeys.USERBROWSER_VERSION);
	}

	/****
	 * 取客户端的os
	 * 
	 * @return
	 */
	public String getUserOS() {
		return (String) ThreadContextManager.get(Contexkeys.USEROS);
	}

	/****
	 * 取客户端的IP
	 * 
	 * @return
	 */
	public String getUserIP() {
		return (String) ThreadContextManager.get(Contexkeys.USERIP);
	}

	/****
	 * 取当前登陆用户
	 * 
	 * @return
	 */
	public User getUser() {
		return (User) ThreadContextManager.get(Contexkeys.USER);

	}

	/****
	 * 取当前登陆用户id
	 * 
	 * @return
	 */
	public String getUserId() {
		return (String) ThreadContextManager.get(Contexkeys.USERID);

	}

	/****
	 * 取当前登陆用户姓名
	 * 
	 * @return
	 */
	public String getUsername() {
		return (String) ThreadContextManager.get(Contexkeys.USERNAME);
	}

	/****
	 * 处理不分页的查询情况
	 * 
	 * @param list
	 * @return
	 */
	public PageVO handleOnePageList(List<DBObject> list) {
		int num = 0;

		if (list == null || list.size() == 0) {
			num = 0;
		} else {
			num = list.size();
		}

		return PageSearchResultHandler.handleDBObjList(list, 1, num, num);

	}
}
