package com.misproto.modules.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import mou.mongodb.FindBatchUtil;
import mou.mongodb.FindOneUtil;
import mou.mongodb.MongoClientManager;
import mou.mongodb.MongoCollectionUtil;

import com.misproto.common.cash.context.Contexkeys;
import com.misproto.common.cash.context.ThreadContextManager;
import com.misproto.common.globalhandler.PageSearchResultHandler;
import com.misproto.common.globalobj.PageVO;
import com.misproto.common.system.DBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.WriteResult;

/****
 * 公共基础持久层操作类，提供通用的持久层操作接口实现
 * 
 * @author NBQ
 *
 */
public abstract class BaseDao implements IBaseDao {

	public abstract String getCollectionName();

	@Override
	public boolean isExist(String _id) {
		return FindOneUtil.isExist(getCollectionName(), _id);
	}

	@Override
	public <T> T findOneByIdObject(String _id, Class<T> clazz) {

		return FindOneUtil.findOneByIdObject(getCollectionName(), _id, clazz);
	}

	@Override
	public DBObject findOneByIdPart(String _id, DBObject returnFields) {

		return FindOneUtil.findOneByIdFields(getCollectionName(), _id,
				returnFields);
	}

	@Override
	public <T> T findOneByConditionObject(DBObject queryCondition,
			Class<T> clazz) {

		return FindOneUtil.findOneByConditionObject(getCollectionName(),
				queryCondition, clazz);
	}

	@Override
	public DBObject findOneByConditionPart(DBObject queryCondition,
			DBObject returnFields) {

		return FindOneUtil.findOneByConditionDBObject(getCollectionName(),
				queryCondition, returnFields);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageVO batchSearchPage(DBObject query, DBObject sort,
			DBObject returnFields) {

		Map<String, Integer> pageInfo = (Map<String, Integer>) ThreadContextManager
				.get(Contexkeys.PAGEINFO_SEARCH);

		List<DBObject> list = MongoCollectionUtil.findBatchPageDBObject(
				getCollectionName(), query, returnFields, pageInfo, sort);

		if (query == null || query.keySet().isEmpty()) {
			long totalNum = DBManager.getCollectionSize(getCollectionName());
			pageInfo.put("total", Integer.parseInt(String.valueOf(totalNum)));
		} else {
			if (pageInfo.get("total") == null) {
				pageInfo.put("total", Integer.parseInt(String
						.valueOf(MongoCollectionUtil.findBatchCount(
								getCollectionName(), query))));
			}
		}

		return PageSearchResultHandler.handleDBObjList(list,
				pageInfo.get("curpagenum"), pageInfo.get("pagecount"),
				pageInfo.get("total"));
	}

	@Override
	public List<DBObject> batchSearch(DBObject query, DBObject sort,
			DBObject returnFields) {
		return FindBatchUtil.findBatchDBObject(getCollectionName(), query,
				returnFields, sort);
	}

	@Override
	public <T> List<T> findBatchObject(DBObject query, Class<T> clazz,
			DBObject sort) {

		return FindBatchUtil.findBatchObject(getCollectionName(), query, clazz,
				sort);
	}

	@Override
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields, int count) {

		Map<String, Integer> pageInfo = new HashMap<String, Integer>();
		pageInfo.put("curpagenum", 1);
		pageInfo.put("pagecount", count);

		List<DBObject> list = FindBatchUtil.findBatchPageDBObject(
				getCollectionName(), query, returnFields, pageInfo, sort);

		int num = count;

		if (list == null || list.size() == 0) {
			num = 0;
		} else {
			if (list.size() < count) {
				num = list.size();
			}
		}

		return PageSearchResultHandler.handleDBObjList(list, 1, count, num);
	}

	@Override
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields) {

		List<DBObject> result = this.batchSearch(query, sort, returnFields);

		int resultCount = 0;

		if (result != null && result.size() > 0) {
			resultCount = result.size();
		}

		return PageSearchResultHandler.handleDBObjList(result, 1, resultCount,
				resultCount);
	}

	@Override
	public String insertObj(Object obj) {

		WriteResult wr = MongoCollectionUtil
				.insertObj(getCollectionName(), obj);

		if (wr == null || wr.getUpsertedId() == null) {
			return null;
		}

		return wr.getUpsertedId().toString();
	}

	@Override
	public BulkWriteResult batchInsertObjs(List<? extends Object> objs,
			boolean isOrder) {

		return MongoCollectionUtil.batchInsertObjs(getCollectionName(), objs,
				isOrder);
	}

	@Override
	public DBObject updateOneByCondition(DBObject query, DBObject returnFields,
			DBObject update, boolean upsert) {
		DBCollection collection = MongoClientManager
				.getCollection(getCollectionName());

		return collection.findAndModify(query, returnFields, null, false,
				update, true, upsert);
	}

	@Override
	public DBObject updateOneById(String _id, DBObject returnFields,
			DBObject update) {

		if (!ObjectId.isValid(_id)) {
			return null;
		}

		DBCollection collection = MongoClientManager
				.getCollection(getCollectionName());

		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(_id));

		return collection.findAndModify(query, returnFields, null, false,
				update, true, false);
	}

	@Override
	public DBObject findAndRemoveOneById(String _id) {

		if (!ObjectId.isValid(_id)) {
			return null;
		}

		return MongoCollectionUtil.findAndRemoveById(getCollectionName(), _id);
	}

	@Override
	public DBObject findAndRemoveOneByIdLogic(String _id, DBObject updateSet) {
		if (!ObjectId.isValid(_id)) {
			return null;
		}

		DBObject update = new BasicDBObject();

		updateSet.put("delflg", "1");
		updateSet.put("useflg", "0");

		update.put("$set", updateSet);

		DBObject returnFields = new BasicDBObject();
		returnFields.put("delflg", "1");
		returnFields.put("useflg", "1");

		return updateOneById(_id, returnFields, update);
	}

	@Override
	public WriteResult removeByIdsLogic(List<String> _ids, DBObject updateSet) {

		if (_ids == null || _ids.isEmpty()) {
			return null;
		}
		DBObject update = new BasicDBObject();
		update.put("delflg", "1");

		updateSet.put("delflg", "1");
		updateSet.put("useflg", "0");

		update.put("$set", updateSet);

		List<ObjectId> oids = new ArrayList<ObjectId>();
		DBObject inSet = new BasicDBObject();
		for (String _id : _ids) {

			int idx = _id.indexOf("$oid\":\"");
			if (idx >= 0) {
				_id = _id.substring(idx + 7, idx + 31);
			}

			if (!ObjectId.isValid(_id)) {
				return null;
			}
			oids.add(new ObjectId(_id));
		}

		inSet.put(QueryOperators.IN, oids);
		DBObject query = new BasicDBObject();
		query.put("_id", inSet);

		return MongoCollectionUtil.updateByCondition(getCollectionName(),
				query, update);
	}

	@Override
	public WriteResult removeByIds(List<String> _ids) {

		return MongoCollectionUtil.removeByIds(getCollectionName(), _ids);
	}
}
