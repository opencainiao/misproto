package test;

import org.junit.Test;

import com.misproto.common.util.PinyinUtil;

public class PinyinTest {

	@Test
	public void testPinY(){
		String name = "我爱北京天安门";
		
		// 关键字的全拼
		String nameFullPy = PinyinUtil.str2Pinyin(name, null);

		// 关键字的简拼
		String nameShortPy = PinyinUtil.strFirst2Pinyin(name);
		
		System.out.println(nameFullPy);
		System.out.println(nameShortPy);
	}
}
