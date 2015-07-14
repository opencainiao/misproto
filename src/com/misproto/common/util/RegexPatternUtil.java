package com.misproto.common.util;

import java.util.regex.Pattern;

import org.mou.common.StringUtil;

public class RegexPatternUtil {

	/***
	 * 获取用于like的正则表达式
	 * 
	 * @param strIn
	 * @return
	 */
	public static Pattern getLikePattern(String strIn) {

		String use = null;

		if (strIn == null || strIn.trim().length() == 0) {
			use = "";
		} else {
			use = strIn.trim();
		}

		return Pattern.compile(".*" + use + ".*", Pattern.CASE_INSENSITIVE);
	}

	/***
	 * 获取用于等于的正则表达式，（忽略大小写）
	 * 
	 * @param strIn
	 * @return
	 */
	public static Pattern getEqualPattern(String strIn) {

		String use = null;

		if (strIn == null || strIn.trim().length() == 0) {
			use = "";
		} else {
			use = strIn.trim();
		}

		return Pattern.compile(use, Pattern.CASE_INSENSITIVE);
	}

	/****
	 * 判断一个字符串是否只包含26个字母（大小写）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isOnlyZimu(String str) {

		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return str.matches("^[A-Za-z]+$");
	}
}
