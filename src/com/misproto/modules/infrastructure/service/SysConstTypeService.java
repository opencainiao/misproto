package com.misproto.modules.infrastructure.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Service;

import com.misproto.common.globalobj.PageVO;
import com.misproto.modules.base.BaseService;
import com.misproto.modules.infrastructure.dao.SysConstTypeDao;
import com.misproto.modules.infrastructure.model.SysConstType;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/****
 * 系统常量类型服务实现
 * 
 * @author NBQ
 *
 */
@Service("sysConstTypeService")
public class SysConstTypeService extends BaseService implements
		ISysConstTypeService {

	@Resource(name = "sysconsttypedao")
	private SysConstTypeDao sysconsttypedao;

	@Resource(name = "autoIncreaserService")
	private AutoIncreaserService autoIncreaserService;

	private static final Logger logger = LogManager
			.getLogger(SysConstTypeService.class);

	@Override
	public SysConstType findOneByIdObject(String _id) {

		return this.sysconsttypedao.findOneByIdObject(_id, SysConstType.class);
	}

	@Override
	public PageVO batchSearchPage(DBObject queryCondition, DBObject sort,
			DBObject returnFields) {
		return this.sysconsttypedao.batchSearchPage(queryCondition, sort,
				returnFields);
	}

	@Override
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields) {
		return this.sysconsttypedao.batchSearchOnePage(query, sort,
				returnFields);
	}

	@Override
	public String add(SysConstType sysconsttype) {

		int id = this.autoIncreaserService
				.getAutoIncreasedInteger("sysconsttypecode");
		sysconsttype.setTypecode(StringUtil.addCharL(id, 5, "0"));

		this.setCreateInfo(sysconsttype);
		return this.sysconsttypedao.insertObj(sysconsttype);
	}

	@Override
	public DBObject updatePart(DBObject returnFields, SysConstType sysconsttype) {

		DBObject toUpdate = makeUpdate(sysconsttype);
		return this.sysconsttypedao.updateOneById(sysconsttype.get_id_str(),
				returnFields, toUpdate);
	}

	/****
	 * 转化对象为要更新的部分字段
	 * 
	 * @param update
	 * @return
	 */
	private DBObject makeUpdate(SysConstType sysconsttype) {

		DBObject update = new BasicDBObject();
		DBObject updateSet = new BasicDBObject();

		updateSet.put("typename", sysconsttype.getTypename());

		this.setModifyInfo(updateSet);
		update.put("$set", updateSet);

		logger.debug("更新的对象信息\n{}", update);
		return update;
	}

	@Override
	public DBObject RemoveOneById(String _id) {
		return this.sysconsttypedao.findAndRemoveOneById(_id);
	}

	@Override
	public DBObject RemoveOneByIdLogic(String _id) {

		DBObject updateSet = new BasicDBObject();
		this.setModifyInfo(updateSet);
		return this.sysconsttypedao.findAndRemoveOneByIdLogic(_id, updateSet);
	}

	@Override
	public boolean isExistSameTypecode(String typecode) {

		DBObject returnFields = new BasicDBObject();
		returnFields.put("_id", 1);

		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("typecode", typecode);
		queryCondition.put("useflg", "1");

		DBObject result = this.sysconsttypedao.findOneByConditionPart(
				queryCondition, returnFields);

		logger.debug("根据类型码查询结果[{}]", result);
		if (result != null && result.get("_id") != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isExistSameTypename(String typename) {

		DBObject returnFields = new BasicDBObject();
		returnFields.put("_id", 1);

		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("typename", typename);
		queryCondition.put("useflg", "1");

		DBObject result = this.sysconsttypedao.findOneByConditionPart(
				queryCondition, returnFields);

		logger.debug("根据类型名查询结果[{}]", result);

		if (result != null && result.get("_id") != null) {
			return true;
		}

		return false;
	}

	@Override
	public SysConstType findOneByTypecode(String typecode) {

		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("typecode", typecode);
		queryCondition.put("useflg", "1");

		SysConstType sysconsttype = this.sysconsttypedao.findOneByConditionObject(
				queryCondition, SysConstType.class);

		return sysconsttype;
	}

}
