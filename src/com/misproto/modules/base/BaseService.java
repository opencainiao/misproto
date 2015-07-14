package com.misproto.modules.base;

import org.mou.common.DateUtil;

import com.misproto.common.cash.context.Contexkeys;
import com.misproto.common.cash.context.ThreadContextManager;
import com.misproto.common.util.ValidateUtil;
import com.misproto.modules.user.model.User;
import com.mongodb.DBObject;

public class BaseService implements IBaseService {

	/****
	 * 判断是否合法的objectId
	 * 
	 * @param _id
	 * @return
	 */
	public boolean isValidObjId(String _id) {
		return ValidateUtil.isValidObjId(_id);
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
	 * 设置对象创建信息
	 * 
	 * @param model
	 */
	public void setCreateInfo(BaseModel model) {
		String date = DateUtil.getCurdate();
		String time = DateUtil.getCurrentTimsmp();
		String userid = this.getUserId();
		String username = this.getUsername();

		model.setCuserid(userid);
		model.setCusername(username);
		model.setCtime(time);
		model.setCdate(date);
	}

	/****
	 * 设置对象创建信息
	 * 
	 * @param model
	 */
	public void setCreateInfo(BaseModel model, String time) {
		String userid = this.getUserId();
		String username = this.getUsername();

		model.setCuserid(userid);
		model.setCusername(username);
		model.setCtime(time);
		model.setCdate(time.substring(0, 10));
	}

	/****
	 * 设置更新信息
	 */
	@Override
	public void setModifyInfo(DBObject dbObject) {

		dbObject.put("lmuserid", this.getUserId());
		dbObject.put("lmusername", this.getUsername());
		dbObject.put("lastoptime", DateUtil.getCurrentTimsmp());
	}

}
