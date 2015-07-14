package com.misproto.common.util;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

public class MongoObjectUtil {

	/****
	 * 取list的某个属性，组成一个新的list
	 * 
	 * @param list
	 * @param attributename
	 * @return
	 */
	public static List<Object> getAttributeList(List<DBObject> list,
			String attributename) {

		if (list == null || list.size() == 0) {
			return null;
		}
		
		List<Object> rtnList = new ArrayList<Object>();

		for (DBObject dbo : list) {
			rtnList.add(dbo.get(attributename));
		}

		return rtnList;
	}
}
