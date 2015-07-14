package test;

import org.apache.commons.lang3.StringEscapeUtils;

/****
 * &#47; -- 左斜杠 /
 * 
 * &#92; -- 右斜杠 \
 * 
 * &#58; -- 冒号:
 * 
 * &#34; 和 &quot; -- 双引号 "
 * 
 * &apos; -- 单引号 '
 * 
 * &#38; -- 与 &
 * 
 * &#123; -- {
 * 
 * &#125; --}
 * 
 * &#124;--|
 * 
 * @author NBQ
 *
 */
public class CharacterEscape {

	public static String escapeHtmlMou(String str) {

		System.out.println("替换前\n" + str);
		// str = str.replaceAll("\\\\", "\\\\\\\\");
		str = str.replaceAll("\\\\", "&#92;");
		str = str.replaceAll("/", "&#47;");
		str = str.replaceAll(":", "&#58;");
		str = str.replaceAll("\\{", "&#123;");
		str = str.replaceAll("\\}", "&#125;");
		str = str.replaceAll("\\|", "&#124;");
		str = str.replaceAll("'", "&apos;");

		System.out.println("替换后\n" + str);
		return str;
	}

	public static void main(String[] args) {

		String aa = "a_$&<>=\":;,\''(){}\\/.中国你好||";

		// System.out.println(aa);
		//
		// String bb = StringEscapeUtils.escapeHtml4(aa);
		//
		// System.out.println(bb);

		escapeHtmlMou(aa);
		// bb = StringEscapeUtils.escapeEcmaScript(aa);

		// System.out.println(bb);
	}
}
