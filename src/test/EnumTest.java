package test;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.misproto.common.util.EnumUtils;
import com.misproto.modules.user.enumes.UserState;

public class EnumTest {

	@Test
	public void testUserState(){
		
		
		Map<String, String> propmap = EnumUtils.enumProp2Map(
				UserState.class,  "code", "name");
		System.out.println(propmap);
	}
	@Test
	public void testEnum() {

		// [0, 1, 2, 3]
		List<Integer> values = EnumUtils.enum2Ordinal(ChanelTypeForTest.class);
		System.out.println(values);

		// [NAV_CHANNEL, TOPIC_LIST, TOPIC_CONTENT, TOPIC_IMG]
		List<String> names = EnumUtils.enum2Name(ChanelTypeForTest.class);
		System.out.println(names);

		// {0=NAV_CHANNEL, 1=TOPIC_LIST, 2=TOPIC_CONTENT, 3=TOPIC_IMG}
		Map<Integer, String> aa = EnumUtils
				.enum2BasicMap(ChanelTypeForTest.class);
		System.out.println(aa);

		// [导航栏目, 文章列表栏目, 文章内容栏目, 图片列表栏目]
		List<String> propNames = EnumUtils.enumProp2List(
				ChanelTypeForTest.class, "name");
		System.out.println(propNames);

		// {0=导航栏目, 1=文章列表栏目, 2=文章内容栏目, 3=图片列表栏目}
		Map<Integer, String> ordimap = EnumUtils.enumProp2OrdinalMap(
				ChanelTypeForTest.class, "name");
		System.out.println(ordimap);

		// {TOPIC_LIST=文章列表栏目, TOPIC_IMG=图片列表栏目, TOPIC_CONTENT=文章内容栏目,
		// NAV_CHANNEL=导航栏目}
		Map<String, String> nammap = EnumUtils.enumProp2NameMap(
				ChanelTypeForTest.class, "name");
		System.out.println(nammap);

		//
		Map<String, String> propmap = EnumUtils.enumProp2Map(
				ChanelTypeForTest.class, "name", "value");
		System.out.println(propmap);
		
		
	}
}

