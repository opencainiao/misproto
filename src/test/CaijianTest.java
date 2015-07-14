package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.mou.common.JsonUtil;
import org.slf4j.impl.StaticLoggerBinder;

import com.misproto.common.util.RegexPatternUtil;

public class CaijianTest {

	public static void crop(int width, int height) {
		StaticLoggerBinder aa = null;
		try {
			Thumbnails.of("F:\\项目\\饶阳果蔬微网站\\图片\\zzdh000.png")
					// 从原图哪里开始裁剪 裁减多少
					.sourceRegion(Positions.CENTER, 400, 400)
					// 新图的大小
					.size(width, height).keepAspectRatio(false)
					.toFile("F:\\项目\\饶阳果蔬微网站\\图片\\zzdh000_1.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		

		List<String> names = new ArrayList<String>();
		names.add("李四");
		names.add("张三");
		names.add("颜色");

		System.out.println(JsonUtil.toJsonStr(names));

		Collections.sort(names);
		System.out.println(JsonUtil.toJsonStr(names));

		if (names.size() > 0) {
			return;
		}
		// crop(110, 70);

		String aa = "张三abC";
		System.out.println(aa);
		System.out.println(aa.toUpperCase());

		// 替换非英文字符为空格
		aa = "张三12abc";

		String bb = aa.replaceAll("[^a-zA-Z]", "");
		System.out.println(aa);
		System.out.println("bb[" + bb + "]");
		System.out.println("abc---" + RegexPatternUtil.isOnlyZimu("abc"));
		System.out.println("ABCefg---" + RegexPatternUtil.isOnlyZimu("ABCefg"));
		System.out.println("123--" + RegexPatternUtil.isOnlyZimu("123"));
		System.out.println("1ab--" + RegexPatternUtil.isOnlyZimu("1ab"));
		System.out.println("张三ab--" + RegexPatternUtil.isOnlyZimu("张三ab"));
		System.out.println("张三--" + RegexPatternUtil.isOnlyZimu("张三ab"));

	}
}
