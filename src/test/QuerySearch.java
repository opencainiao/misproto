package test;

import java.util.regex.Pattern;

import org.mou.common.StringUtil;

import com.misproto.common.util.RegexPatternUtil;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/****
 * 通用查询写法
 * 
 * @author NBQ
 *
 */
public class QuerySearch {

	public void or() {

		String[] conditionstr = new String[] { "张三", "李四" };
		DBObject query = new BasicDBObject();

		BasicDBList condList = new BasicDBList();

		for (int i = 0; i < conditionstr.length; ++i) {
			String item = conditionstr[i];
			// 模糊查询
			Pattern namePattern = RegexPatternUtil.getLikePattern(item);
			condList.add(new BasicDBObject("matcode", namePattern));
			condList.add(new BasicDBObject("matname", namePattern));
			condList.add(new BasicDBObject("aliasname", namePattern));
		}

		query.put("$or", condList);
	}
}
