package com.misproto.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.types.ObjectId;
import org.mou.common.StringUtil;

public class ValidateUtil {

	/****
	 * 判断是否合法的objectId
	 * 
	 * @param _id
	 * @return
	 */
	public static boolean isValidObjId(String _id) {

		if (StringUtil.isEmpty(_id)) {
			return false;
		}

		return ObjectId.isValid(_id);
	}

	/****
	 * 判断一个字符串是否全是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/****
	 * 判断一个字符串是否是小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("^\\d+\\.\\d+$");

		java.util.regex.Matcher m2 = pattern.matcher(str);
		if (m2.matches()) {
			return true;
		}

		return false;
	}

	/****
	 * 判断是否是数字或是小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericOrDouble(String str) {
		if (isNumeric(str) || isDouble(str)) {
			return true;

		}

		return false;
	}

	public static void main(String[] args) {
		System.out.println("is_double------------");
		System.out.println(isDouble("123"));
		System.out.println(isDouble("123.1"));
		System.out.println(isDouble("123.10"));
		System.out.println(isDouble("123.1.0"));
		System.out.println("is_numeric------------");
		System.out.println(isNumeric("123"));
		System.out.println(isNumeric("123.1"));
		System.out.println(isNumeric("123.10"));
		System.out.println(isNumeric("123.1.0"));
		System.out.println("is_numeric_or_double------------");
		System.out.println(isNumericOrDouble("123"));
		System.out.println(isNumericOrDouble("123.1"));
		System.out.println(isNumericOrDouble("123.10"));
		System.out.println(isNumericOrDouble("123.1.0"));
	}
}
