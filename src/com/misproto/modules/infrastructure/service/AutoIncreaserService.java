package com.misproto.modules.infrastructure.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.misproto.modules.infrastructure.dao.AutoIncreaserDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service("autoIncreaserService")
public class AutoIncreaserService implements IAutoIncreaserService {

	@Resource(name = "autoIncreaserDao")
	private AutoIncreaserDao autoIncreaserDao;

	@Override
	public int getAutoIncreasedInteger(String mainkey) {

		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("mainkey", mainkey);// 自增标识

		DBObject returnFields = new BasicDBObject();
		returnFields.put("val", 1);// 自增值

		DBObject update = new BasicDBObject();
		update.put("$inc", new BasicDBObject("val", 1));

		DBObject result = autoIncreaserDao.updateOneByCondition(queryCondition,
				returnFields, update, true);

		return  (Integer)(result.get("val"));
	}

}
