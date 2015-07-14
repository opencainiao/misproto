package com.misproto;

public class Constant {

	// 文章状态
	public static String ARTICLE_STATUS_WFB = "0";// 未发布
	public static String ARTICLE_STATUS_YFB = "1";// 已发布

	// Session级缓存的属性key常量
	public static String SESSION_CASH_PUBLICDATA = "SCPD";

	public static String USER_REGIST_NEED_ACTIVE_FLG = "1";// 1-需要激活，0 不需要激活

	public static String UNITSTATUS_USED = "1";// 启用
	public static String UNITSTATUS_STOPPED = "2";// 已停用
	public static String UNITSTATUS_DELETED = "0";// 已删除

	public static String USER_CHARACTER_SYS = "1";// 1.系统后台人员
	public static String USER_CHARACTER_FRONT = "2";// 1.前端注册用户
}
