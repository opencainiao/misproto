package com.misproto.modules.user.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.StringUtil;
import org.mou.common.security.EncryptMou;
import org.springframework.stereotype.Service;

import com.misproto.Constant;
import com.misproto.modules.user.dao.UserDao;
import com.misproto.modules.user.enumes.RegistResult;
import com.misproto.modules.user.enumes.UserActiveResult;
import com.misproto.modules.user.enumes.UserCharacter;
import com.misproto.modules.user.enumes.UserState;
import com.misproto.modules.user.model.User;

/****
 * 用户注册与激活
 * 
 * @author NBQ
 *
 */
@Service("userRegisterService")
public class UserRegisterService implements IUserRegisterService {

	@Resource(name = "userService")
	private IUserService userService;

	@Resource(name = "userdao")
	private UserDao userdao;

	private static final Logger logger = LogManager
			.getLogger(UserRegisterService.class);

	/****
	 * 用邮箱注册
	 */
	@Override
	public String registWithEmail(User registeruser) {

		String email = registeruser.getEmail();
		// 1.如果邮箱已被注册，直接返回
		User registeruser1 = userService.getUserInfByUserEmail(email);
		if (registeruser1 != null) {
			return RegistResult.EXIST.getCode();
		}

		String time = DateUtil.getCurrentTimsmp();

		// 密码加密
		String newPasswordHashed = EncryptMou.encrypt(registeruser
				.getPassword());
		registeruser.setPassword(newPasswordHashed);

		String activecode = EncryptMou.encrypt(email);

		// 设置激活信息
		if (Constant.USER_REGIST_NEED_ACTIVE_FLG.equals("1")) {
			registeruser.setState(UserState.NOTACTIVE);

			registeruser.setActivecode(activecode);
		} else {
			registeruser.setState(UserState.ACTIVE);

			registeruser.setActivecode("-");
			registeruser.setActivetime(time);
		}

		registeruser.setCharacter(UserCharacter.NORMAL_REGIST_USER);

		String date = DateUtil.getCurdate();
		registeruser.setCdate(date);
		registeruser.setCtime(time);
		registeruser.setLastoptime(time);

		// 持久化数据
		String _id = userdao.insertObj(registeruser);

		return _id + "_" + activecode;
	}

	@Override
	public String active(String email, String activecode) {
		if (StringUtil.isEmpty(email) || StringUtil.isEmpty(activecode)) {
			return null;
		}

		String time = DateUtil.getCurrentTimsmp();

		User user = this.userService.getUserInfByUserEmail(email);
		if (user == null) {
			return null;
		}

		String registtime = user.getCtime();

		int intervalMinutes = DateUtil.getIntervalTimeStampMinute(time,
				registtime);

		// 间隔大于48小时，返回
		if (intervalMinutes > 48 * 60) {
			logger.debug("超时,[{}]分钟（[{}]小时）\n[{},\n{}]", intervalMinutes,
					intervalMinutes * 1.0 / 60, registtime, time);
			return UserActiveResult.TIMEOUT.getCode();
		}

		// 激活码不一致，返回
		if (!user.getActivecode().equals(activecode)) {
			return UserActiveResult.ACTIVECODEERROR.getCode();
		}

		if (user.isNotActive()) {
			this.userService.updatestatus(user.get_id_str(), UserState.ACTIVE,
					time);
		}

		return UserActiveResult.SUCCESS.getCode();
	}
}
