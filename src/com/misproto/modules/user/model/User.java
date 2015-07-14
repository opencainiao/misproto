package com.misproto.modules.user.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.mou.common.DateUtil;
import org.mou.common.StringUtil;

import com.misproto.modules.base.BaseModel;
import com.misproto.modules.base.enumes.Sex;
import com.misproto.modules.user.enumes.UserCharacter;
import com.misproto.modules.user.enumes.UserState;

/****
 * 系统用户
 * 
 * @author NBQ
 *
 */
public class User extends BaseModel {

	private String username;// 用户登录名称
	private String password;// 用户登录密码
	private String nick;// 用户的昵称
	private String email;// 用户的邮件
	private String phone;// 用户的联系电话

	private String sex; // 1-男，0-女
	private String state;// 用户状态
	private String statename; // 状态名称
	private String character;// 用户性质 1.系统后台人员 2.前端注册用户
	private String charactername; // 用户性质名称

	private String activetime; // 激活时间
	private String activecode; // 激活码
	private String resetpwdapptime; // 重置密码申请时间
	private String resetpwdcode; // 重置密码的重置码

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotEmpty(message = "用户密码不能为空")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotEmpty(message = "昵称不能为空")
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@NotEmpty(message = "邮箱不能为空")
	@Email(message = "邮件格式不正确")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getActivetime() {
		return activetime;
	}

	public void setActivetime(String activetime) {
		this.activetime = activetime;
	}

	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	public String getResetpwdapptime() {
		return resetpwdapptime;
	}

	public void setResetpwdapptime(String resetpwdapptime) {
		this.resetpwdapptime = resetpwdapptime;
	}

	public String getResetpwdcode() {
		return resetpwdcode;
	}

	public void setResetpwdcode(String resetpwdcode) {
		this.resetpwdcode = resetpwdcode;
	}

	public String valideNoEmptyForEmailRegister() {

		String email = this.getEmail();

		if (StringUtil.isEmpty(email)) {
			return "邮箱不能为空";
		} else if (StringUtil.isEmpty(this.getPassword())) {
			return "密码不能为空";
		} else if (StringUtil.isEmpty(this.getNick())) {
			return "昵称不能为空";
		}

		return "";
	}

	public Map<String, String> toSummary() {
		Map<String, String> summarymap = new HashMap<String, String>();
		summarymap.put("uname", this.username);
		summarymap.put("nick", this.nick);
		summarymap.put("email", this.email);
		summarymap.put("phone", this.phone);

		summarymap.put("state", this.state);
		summarymap.put("tatename", this.statename);
		summarymap.put("character", this.character);
		summarymap.put("charactername", this.charactername);

		return summarymap;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setCdate(DateUtil.getCurdate());
		user.setUsername("张三");
		user.setEmail("nihao@qq.com");

		System.out.println(user);
	}

	public String getState() {
		return state;
	}

	public void setState(UserState userstate) {

		this.state = userstate.getCode();
		setStatename(userstate.getName());
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(UserCharacter usercharacter) {

		this.character = usercharacter.getCode();
		setCharactername(usercharacter.getName());
	}

	public String getCharactername() {
		return charactername;
	}

	public void setCharactername(String charactername) {
		this.charactername = charactername;
	}

	/****
	 * 判断是否为未激活状态
	 * 
	 * @return
	 */
	public boolean isNotActive() {

		if (UserState.NOTACTIVE.getCode().equals(this.state)) {
			return true;
		}

		return false;
	}

	/****
	 * 判断是否为未激活状态
	 * 
	 * @return
	 */
	public boolean isActive() {

		if (UserState.ACTIVE.getCode().equals(this.state)) {
			return true;
		}

		return false;
	}

	/****
	 * 是否合法用户
	 * 
	 * @return
	 */
	public boolean isValidUser() {

		if (UserState.NOTACTIVE.equals(this.state)
				|| UserState.ACTIVE.equals(this.state)) {
			return true;
		}

		return false;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = Sex.getSexByCode(sex).getCode();
	}

}
