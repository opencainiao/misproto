package com.misproto.common.globalhandler;

public class SpecialCharHandler {

	/****
	 * json字符串中有特殊字符的话，进行替换，前端页面显示
	 * 
	 * @param s
	 * @return
	 */
	public static String String2Json(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.toCharArray()[i];
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				if ((c >= 0 && c <= 31) || c == 127)// 在ASCⅡ码中，第0～31号及第127号(共33个)是控制字符或通讯专用字符
				{

				} else {
					sb.append(c);
				}
				break;
			}
		}
		return sb.toString();
	}
}
